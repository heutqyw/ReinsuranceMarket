/*
Copyright DTCC 2016 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.pingan.reinsurance;

import com.alibaba.fastjson.JSONObject;
import com.pingan.reinsurance.Dao.FacingPolicyDao;
import com.pingan.reinsurance.Dao.FacingTreatyDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.java.shim.ChaincodeBase;
import org.hyperledger.java.shim.ChaincodeStub;

public class Reinsurance extends ChaincodeBase {
	private static Log log = LogFactory.getLog(Reinsurance.class);
	private FacingPolicyDao facingPolicyDao = new FacingPolicyDao();
	private FacingTreatyDao facingTreatyDao = new FacingTreatyDao();


	@Override
	public String run(ChaincodeStub stub, String function, String[] args) {
		log.info("In run, function:"+function);

		switch (function) {
			case "init":
				init(stub, function, args);
				break;
			case "transfer":
				String re = transfer(stub, args);
				System.out.println(re);
				return re;
			case "put":
				for (int i = 0; i < args.length; i += 2)
					stub.putState(args[i], args[i + 1]);
				break;
			case "del":
				for (String arg : args)
					stub.delState(arg);
				break;
			case "createCompany":
				return createCompany(stub, args);
			case "saveFacingTreaty":
				facingTreatyDao.insertRow(stub, args,false);
				break;
			case "editFacingTreaty":
				facingTreatyDao.insertRow(stub, args,true);
				break;
			case "saveFacingPolicy":
				facingPolicyDao.insertRow(stub, args,false);
				break;
			case "editFacingPolicy":
				facingPolicyDao.insertRow(stub, args,true);
				break;
			case "deleteFacingPolicy":
				facingPolicyDao.delete(stub, args);
				break;
			case "deleteFacingTreaty":
				facingTreatyDao.delete(stub, args);
				break;
			default:
				return transfer(stub, args);
		}

		return null;
	}

	private String  transfer(ChaincodeStub stub, String[] args) {
		System.out.println("in transfer");
		if(args.length!=3){
			System.out.println("Incorrect number of arguments:"+args.length);
			return "{\"Error\":\"Incorrect number of arguments. Expecting 3: from, to, amount\"}";
		}
		String fromName =args[0];
		String fromAm=stub.getState(fromName);
		String toName =args[1];
		String toAm=stub.getState(toName);
		String am =args[2];
		int valFrom=0;
		if (fromAm!=null&&!fromAm.isEmpty()){
			try{
				valFrom = Integer.parseInt(fromAm);
			}catch(NumberFormatException e ){
				System.out.println("{\"Error\":\"Expecting integer value for asset holding of "+fromName+" \"}"+e);
				return "{\"Error\":\"Expecting integer value for asset holding of "+fromName+" \"}";
			}
		}else{
			return "{\"Error\":\"Failed to get state for " +fromName + "\"}";
		}

		int valTo=0;
		if (toAm!=null&&!toAm.isEmpty()){
			try{
				valTo = Integer.parseInt(toAm);
			}catch(NumberFormatException e ){
				e.printStackTrace();
				return "{\"Error\":\"Expecting integer value for asset holding of "+toName+" \"}";
			}
		}else{
			return "{\"Error\":\"Failed to get state for " +toName + "\"}";
		}

		int valA =0;
		try{
			valA = Integer.parseInt(am);
		}catch(NumberFormatException e ){
			e.printStackTrace();
			return "{\"Error\":\"Expecting integer value for amount \"}";
		}
		if(valA>valFrom)
			return "{\"Error\":\"Insufficient asset holding value for requested transfer amount \"}";
		valFrom = valFrom-valA;
		valTo = valTo+valA;
		System.out.println("Transfer "+fromName+">"+toName+" am='"+am+"' new values='"+valFrom+"','"+ valTo+"'");
		stub.putState(fromName,""+ valFrom);
		stub.putState(toName, ""+valTo);

		System.out.println("Transfer complete");

		return null;

	}

	public String init(ChaincodeStub stub, String function, String[] args) {
		if(args.length!=4){
			return "{\"Error\":\"Incorrect number of arguments. Expecting 4\"}";
		}
		try{
			log.info("init <--------------------->");
			int valA = Integer.parseInt(args[1]);
			int valB = Integer.parseInt(args[3]);
			log.info("init valA:<--------------------->"+valA);
			log.info("init args[0]:<--------------------->"+args[0]);
			log.info("init args[1]:<--------------------->"+args[1]);
			stub.putState(args[0], args[1]);
			stub.putState(args[2], args[3]);

			facingPolicyDao.createFacPolicyTable(stub);
			facingTreatyDao.createFacTreatyTable(stub);


		}catch(NumberFormatException e ){
			return "{\"Error\":\"Expecting integer value for asset holding\"}";
		}
		return null;
	}


	@Override
	public String query(ChaincodeStub stub, String function, String[] args) {
		if(args.length!=1){
			return "{\"Error\":\"Incorrect number of arguments. Expecting name of the person to query\"}";
		}
		log.info("query <--------------------->");

//		String am =stub.getState(args[0]);
//		log.info("query am:<--------------------->"+am);
//		if (am!=null&&!am.isEmpty()){
//			try{
//				int valA = Integer.parseInt(am);
//				return  "{\"Name\":\"" + args[0] + "\",\"Amount\":\"" + am + "\"}";
//			}catch(NumberFormatException e ){
//				return "{\"Error\":\"Expecting integer value for asset holding\"}";
//			}		}else{
//			return "{\"Error\":\"Failed to get state for " + args[0] + "\"}";
//		}

//		if(function.equals("getCompanyById")) {
//            return this.getCompanyById(stub, args);
//        }else {
//		    return noMethodWarning();
//        }

		switch (function) {
			case "getCompanyById":
				return getCompanyById(stub, args);
			case "getCompanys":
				return getCompanys(stub, args);
			case "getFacingPolicy":
				return facingPolicyDao.query(stub, args);
			case "getFacingTreaty":
				return facingTreatyDao.query(stub, args);
			default:
				return noMethodWarning();
		}



	}

    private String noMethodWarning() {
        return "noMethodWarning";
    }

	private String getCompanyById(ChaincodeStub stub, String[] args) {
		if(args.length!=1){
			return "{\"Error\":\"Incorrect number of arguments. Expecting name of the person to query\"}";
		}
		String am =stub.getState(args[0]);
		if(am == null){
			am ="am is empty";
		}
		log.info("getCompanyById am:"+am);
		if (am !=null && !am.isEmpty()){
			try{
				int valA = Integer.parseInt(am);
				return  "{\"Name\":\"" + args[0] + "\",\"Amount\":\"" + am + "\"}";
			}catch(NumberFormatException e ){
				return "{\"Error\":\"Expecting integer value for asset holding\"}";
			}		}else{
			return "{\"Error\":\"Failed to get state for " + args[0] + "\"}";
		}

	}

	private String getCompanys(ChaincodeStub stub, String[] args) {
		log.info("getCompanys<------------------>");
		return "";
	}

	private String createCompany(ChaincodeStub stub, String[] args) {
		return "";
	}

	@Override
	public String getChaincodeID() {
		return "Reinsurance";
	}

	public static void main(String[] args) throws Exception {
		new Reinsurance().start(args);
	}


}
