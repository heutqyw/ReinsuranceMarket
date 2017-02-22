package com.pingan.reinsurance.Dao;

import com.pingan.reinsurance.Reinsurance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeStub;
import org.hyperledger.protos.TableProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heutqyw on 2017/2/22.
 */
public class FacingPolicyDao {
    private static Log log = LogFactory.getLog(FacingPolicyDao.class);

    private static final String tableName="FAC_POLICY";

    public void createFacPolicyTable(ChaincodeStub stub){
        log.info("come into method<--------------------->createFacPolicyTable");

        List<TableProto.ColumnDefinition> cols = new ArrayList<TableProto.ColumnDefinition>();

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("policy_no")
                .setKey(true)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("policy_name")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("created_by")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("ceded_status")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("begine_time")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("end_time")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("own_amount")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("own_rate")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("ri_amount")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("description")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        try {
            try {
                stub.deleteTable(tableName);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("createFacPolicyTable deleteTable error:"+e.getMessage());
            }
            stub.createTable(tableName,cols);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("createFacPolicyTable complete");
    }

    public void insertRow(ChaincodeStub stub, String[] args, boolean update) {

        String fieldID = "";

        try {
            if(update){
                fieldID = args[0];
            }else{
                fieldID = stub.getUuid();
            }

        }catch (NumberFormatException e){
            log.error("Illegal field id -" + e.getMessage());
            return;
        }

        TableProto.Column col1 =
                TableProto.Column.newBuilder()
                        .setString(fieldID).build();
        TableProto.Column col2 =
                TableProto.Column.newBuilder()
                        .setString(args[1]).build();
        TableProto.Column col3 =
                TableProto.Column.newBuilder()
                        .setString(args[2]).build();
        TableProto.Column col4 =
                TableProto.Column.newBuilder()
                        .setInt32(Integer.parseInt(args[3])).build();
        TableProto.Column col5 =
                TableProto.Column.newBuilder()
                        .setString(args[4]).build();
        TableProto.Column col6 =
                TableProto.Column.newBuilder()
                        .setString(args[5]).build();
        TableProto.Column col7 =
                TableProto.Column.newBuilder()
                        .setInt32(Integer.parseInt(args[6])).build();
        TableProto.Column col8 =
                TableProto.Column.newBuilder()
                        .setInt32(Integer.parseInt(args[7])).build();
        TableProto.Column col9 =
                TableProto.Column.newBuilder()
                        .setInt32(Integer.parseInt(args[8])).build();
        TableProto.Column col10 =
                TableProto.Column.newBuilder()
                        .setString(args[9]).build();

        List<TableProto.Column> cols = new ArrayList<TableProto.Column>();
        cols.add(col1);
        cols.add(col2);
        cols.add(col3);
        cols.add(col4);
        cols.add(col5);
        cols.add(col6);
        cols.add(col7);
        cols.add(col8);
        cols.add(col9);
        cols.add(col10);


        TableProto.Row row = TableProto.Row.newBuilder()
                .addAllColumns(cols)
                .build();
        try {

            boolean success = false;
            if(update){
                success = stub.replaceRow(tableName,row);
            }else
            {
                success = stub.insertRow(tableName, row);
            }
            if (success){
                log.info("FacingPolicyDao insertRow successfully inserted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String query(ChaincodeStub stub, String[] args){
        log.info("FacingPolicyDao query");
        String fieldID = "";

        try {
            fieldID = args[0];
        }catch (NumberFormatException e){
            log.error("Illegal field id -" + e.getMessage());
            return "ERROR querying ";
        }
        TableProto.Column queryCol =
                TableProto.Column.newBuilder()
                        .setString(fieldID).build();
        List<TableProto.Column> key = new ArrayList<>();
        key.add(queryCol);
        try {
            TableProto.Row tableRow = stub.getRow(tableName,key);
            if (tableRow.getSerializedSize() > 0) {
                return tableRow.getColumns(1).getString();
            }else
            {
                return "No record found !";
            }
        } catch (Exception invalidProtocolBufferException) {
            invalidProtocolBufferException.printStackTrace();
        }
        return "";
    }

}
