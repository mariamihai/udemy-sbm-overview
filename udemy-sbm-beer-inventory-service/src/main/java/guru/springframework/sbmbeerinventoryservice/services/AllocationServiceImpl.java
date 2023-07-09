package guru.springframework.sbmbeerinventoryservice.services;

import guru.springframework.sbmbeerinventoryservice.domain.BeerInventory;
import guru.springframework.sbmbeerinventoryservice.repositories.BeerInventoryRepository;
import guru.springframework.sbmbeerinventoryservice.web.model.events.BeerOrderDto;
import guru.springframework.sbmbeerinventoryservice.web.model.events.BeerOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocating OrderId: " + beerOrderDto.getId());

        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(line -> {
            if(needsToAllocateBeer(line)) {
                allocateBeerOrderLine(line);
            }

            totalOrdered.set(totalOrdered.get() + getOrderQuantity(line));
            totalAllocated.set(totalAllocated.get() + getQuantityAllocated(line));
        });

        return totalOrdered.get() == totalAllocated.get();
    }

    private void allocateBeerOrderLine(BeerOrderLineDto line) {
        List<BeerInventory> beerInventoryList = beerInventoryRepository.findAllByUpc(line.getUpc());

        beerInventoryList.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            int orderQuantity = getOrderQuantity(line);
            int allocatedQuantity = getQuantityAllocated(line);
            int quantityToAllocate = orderQuantity - allocatedQuantity;

            if (inventory >= quantityToAllocate) { // Full allocation
                inventory = inventory - quantityToAllocate;
                line.setQuantityAllocated(orderQuantity);

                beerInventory.setQuantityOnHand(inventory);
                beerInventoryRepository.save(beerInventory);
            } else if (inventory > 0) { // Partial allocation
                line.setQuantityAllocated(allocatedQuantity + inventory);
            }

            deleteInventoryIfEmpty(beerInventory);
        });
    }

    private Integer getOrderQuantity(BeerOrderLineDto line) {
        return line.getOrderQuantity() != null ? line.getOrderQuantity() : 0;
    }

    private Integer getQuantityAllocated(BeerOrderLineDto line) {
        return line.getQuantityAllocated() != null ? line.getQuantityAllocated() : 0;
    }

    private boolean needsToAllocateBeer(BeerOrderLineDto line) {
        return getOrderQuantity(line) - getQuantityAllocated(line) > 0;
    }

    private void deleteInventoryIfEmpty(BeerInventory beerInventory) {
        if(beerInventory.getQuantityOnHand() == 0) {
            beerInventoryRepository.delete(beerInventory);
        }
    }
}
