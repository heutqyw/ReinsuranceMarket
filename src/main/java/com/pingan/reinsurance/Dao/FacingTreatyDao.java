package com.pingan.reinsurance.Dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeStub;
import org.hyperledger.protos.TableProto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx-pj on 2017/2/22.
 */
public class FacingTreatyDao {
    private static Log log = LogFactory.getLog(FacingTreatyDao.class);

    private static final String tableName="fac_treaty";

    public void createFacTreatyTable(ChaincodeStub stub){
        log.info("come into method<--------------------->createFacTreatyTable");
        String tableName="FAC_treaty";
        List<TableProto.ColumnDefinition> cols = new ArrayList<TableProto.ColumnDefinition>();

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("fac_code")
                .setKey(true)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("policy_code")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("reinsurer_code")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("reinsurer_ceded")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("fac_amount")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
                .build()
        );

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("createDate")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.STRING)
                .build()
        );


        try {
            try {
                stub.deleteTable(tableName);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("createFacTreatyTable deleteTable error:"+e.getMessage());
            }
            stub.createTable(tableName,cols);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("createFacTreatyTable complete");
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
                        .setInt32(Integer.parseInt(args[4])).build();
        TableProto.Column col6 =
                TableProto.Column.newBuilder()
                        .setString(args[5]).build();


        List<TableProto.Column> cols = new ArrayList<TableProto.Column>();
        cols.add(col1);
        cols.add(col2);
        cols.add(col3);
        cols.add(col4);
        cols.add(col5);
        cols.add(col6);

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
        log.info("FacingTreatyDao query");
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
        return "No record found !";
    }

}
