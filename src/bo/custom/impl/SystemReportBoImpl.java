package bo.custom.impl;

import CrudUtil.CrudUtil;
import bo.custom.SystemReportBo;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SystemReportBoImpl implements SystemReportBo {
    @Override
    public ArrayList<OrderDetailDTO> mostMovableItem() throws Exception, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("select * from orderdetails order by qty desc");
        ArrayList<OrderDetailDTO>allItem=new ArrayList<>();
        while (resultSet.next()) {
            allItem.add(new OrderDetailDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return allItem;
    }

    @Override
    public ArrayList<OrderDetailDTO> leastMovableItem() throws Exception, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(" select * from orderdetails order by qty asc");
        ArrayList<OrderDetailDTO>arrayList=new ArrayList<>();
        while (resultSet.next()){
            arrayList.add(new OrderDetailDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return arrayList;
    }

    @Override
    public ArrayList<OrderDTO> getAllOrderByDaily(String date) throws SQLException, ClassNotFoundException {
        return null;
    }
}
