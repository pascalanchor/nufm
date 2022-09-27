package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIContractIn;
import avh.nufm.api.model.out.APIContractOut;
import avh.nufm.business.model.Contract;

public class ContractTransformer {
	public static Contract ContractToModel(APIContractIn cin) {
		Contract res = new Contract();
		
		return res;
	}

	public static APIContractOut ContractFromModel(Contract contract) {
		APIContractOut res = new APIContractOut();
		
		return res;
	}
}
