package thread.executor.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class OldOrderService {

    public void order(String orderNo) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(3);
        InventoryWork inventoryWork = new InventoryWork(orderNo);
        ShippingWork shippingWork = new ShippingWork(orderNo);
        AccountingWork accountingWork = new AccountingWork(orderNo);
        // 작업 요청
        List<Callable<Boolean>> works = List.of(inventoryWork, shippingWork, accountingWork);
        List<Future<Boolean>> futures = es.invokeAll(works);

        Boolean inventoryResult = futures.get(0)
                                         .get();
        Boolean shippingResult = futures.get(1)
                                        .get();
        Boolean accountingResult = futures.get(2)
                                          .get();

        // 결과 확인
        if (inventoryResult && shippingResult && accountingResult) {
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        } else {
            log("일부 작업이 실패했습니다.");
        }

        es.close();

    }

    static class InventoryWork implements Callable<Boolean> {
        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {
        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {
        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }
}