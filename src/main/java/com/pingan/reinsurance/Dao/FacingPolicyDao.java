package com.pingan.reinsurance.Dao;

import com.alibaba.fastjson.JSON;
import com.pingan.reinsurance.Reinsurance;
import com.pingan.reinsurance.entity.FacingPolicy;
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
                .setType(TableProto.ColumnDefinition.Type.INT32)
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

        cols.add(TableProto.ColumnDefinition.newBuilder()
                .setName("insurable_amount")
                .setKey(false)
                .setType(TableProto.ColumnDefinition.Type.INT32)
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

//        String fieldID = "";
//
//        try {
//            if(update){
//                fieldID = args[0];
//            }else{
//                fieldID = stub.getUuid();
//            }
//
//        }catch (NumberFormatException e){
//            log.error("Illegal field id -" + e.getMessage());
//            return;
//        }
        log.info("facingPolicy insertRow String:"+args[0]);
        FacingPolicy facingPolicy=(FacingPolicy) JSON.parseObject(args[0].toString(), FacingPolicy.class);

        TableProto.Column col1 =
                TableProto.Column.newBuilder()
                        .setInt32(facingPolicy.getPolicyNo()).build();
        TableProto.Column col2 =
                TableProto.Column.newBuilder()
                        .setString(facingPolicy.getPolicyName()).build();
        TableProto.Column col3 =
                TableProto.Column.newBuilder()
                        .setString(facingPolicy.getCreatedBy()).build();
        TableProto.Column col4 =
                TableProto.Column.newBuilder()
                        .setInt32(facingPolicy.getCededStatus()).build();
        TableProto.Column col5 =
                TableProto.Column.newBuilder()
                        .setString(facingPolicy.getBegineTime()).build();
        TableProto.Column col6 =
                TableProto.Column.newBuilder()
                        .setString(facingPolicy.getEndTime()).build();
        TableProto.Column col7 =
                TableProto.Column.newBuilder()
                        .setInt32(facingPolicy.getOwnAmount()).build();
        TableProto.Column col8 =
                TableProto.Column.newBuilder()
                        .setInt32(facingPolicy.getOwnRate()).build();
        TableProto.Column col9 =
                TableProto.Column.newBuilder()
                        .setInt32(facingPolicy.getRiAmount()).build();
        TableProto.Column col10 =
                TableProto.Column.newBuilder()
                        .setString(facingPolicy.getDescription()).build();

        TableProto.Column col11 =
                TableProto.Column.newBuilder()
                        .setInt32(facingPolicy.getInsurableAmount()).build();

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
        cols.add(col11);


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
        log.info("FacingPolicyDao query fieldID:"+args[0]);
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
                //return tableRow.getColumns(1).getString();
                //String result = JSON.toJSONString(tableRow);

                int policyNo= tableRow.getColumns(0).getInt32();
                log.info("FacingPolicyDao query index0 policyNo:"+policyNo);
                String policyName= tableRow.getColumns(1).getString();
                log.info("FacingPolicyDao query index1 policyName:"+policyName);
                String createdBy= tableRow.getColumns(2).getString();
                log.info("FacingPolicyDao query index2 createdBy:"+createdBy);
                int cededStatus= tableRow.getColumns(3).getInt32();
                log.info("FacingPolicyDao query index3 cededStatus:"+cededStatus);
                String begineTime= tableRow.getColumns(4).getString();
                log.info("FacingPolicyDao query index4 begineTime:"+begineTime);
                String endTime= tableRow.getColumns(5).getString();
                int ownAmount= tableRow.getColumns(6).getInt32();
                int ownRate= tableRow.getColumns(7).getInt32();
                int riAmount= tableRow.getColumns(8).getInt32();
                String description= tableRow.getColumns(9).getString();
                int insurableAmount= tableRow.getColumns(10).getInt32();
                log.info("FacingPolicyDao query index10 insurableAmount:"+insurableAmount);

                FacingPolicy facingPolicy = new FacingPolicy();
                facingPolicy.setPolicyNo(policyNo);
                facingPolicy.setPolicyName(policyName);
                facingPolicy.setCededStatus(cededStatus);
                facingPolicy.setBegineTime(begineTime);
                facingPolicy.setCreatedBy(createdBy);
                facingPolicy.setDescription(description);
                facingPolicy.setEndTime(endTime);
                facingPolicy.setOwnAmount(ownAmount);
                facingPolicy.setOwnRate(ownRate);
                facingPolicy.setRiAmount(riAmount);
                facingPolicy.setInsurableAmount(insurableAmount);

                String result = JSON.toJSONString(facingPolicy);
                log.info("FacingPolicyDao query result:"+result);

                return result;
            }else
            {
                return "No record found !";
            }
        } catch (Exception invalidProtocolBufferException) {
            log.info("FacingPolicyDao query result:");
            invalidProtocolBufferException.printStackTrace();
        }
        return "No record found !";
    }

    public boolean delete(ChaincodeStub stub, String[] args){
        String fieldID = args[0];
        log.info("FacingPolicyDao delete fieldID<----------------->:"+fieldID);
        TableProto.Column queryCol =
                TableProto.Column.newBuilder()
                        .setString(fieldID).build();
        List<TableProto.Column> key = new ArrayList<>();
        key.add(queryCol);
        boolean result = stub.deleteRow(tableName, key);
        log.info("FacingPolicyDao delete result<----------------->:"+result);
        return result;
    }

}
