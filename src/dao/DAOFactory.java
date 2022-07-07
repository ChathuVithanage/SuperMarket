package dao;

import dao.custom.impl.CustomerDaoImpl;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailsDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    //singleton
    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    //public final static integer values
    public enum DAOTypes {
        CUSTOMER, ITEM,ORDER_DETAILS,ORDER
    }

    //method for hide the object creation logic and return what client wants
    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDaoImpl(); //SuperDAO superDAO=new CustomerDAOImpl();
            case ITEM:
                return new ItemDaoImpl(); //SuperDAO superDAO=new ItemDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsDAOImpl(); //SuperDAO superDAO = new OrderDetailsDAOImpl();
            case ORDER:
                return new OrderDAOImpl(); //SuperDAO superDAO = new QueryDAOImpl();*/
            default:
            return null;
        }
    }

}
