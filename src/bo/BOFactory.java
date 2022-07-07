package bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBo getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBoImpl(); // SuperBO bo =new CustomerBOImpl();
            case ITEM:
                return new ItemBoImpl(); // SuperBO bo = new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl(); //SuperBO bo = new PurchaseOrderBOImpl();
            case ORDERDETAILS:
                return  new OrderDetailsBoImpl();
            case SYSTEMREPORT:
                return new SystemReportBoImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER, ORDERDETAILS, SYSTEMREPORT;
    }

}
