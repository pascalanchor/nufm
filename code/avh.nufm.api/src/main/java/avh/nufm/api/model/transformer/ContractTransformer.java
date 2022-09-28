package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIContractIn;
import avh.nufm.api.model.out.APIContractOut;
import avh.nufm.business.model.Contract;

public class ContractTransformer {
	public static Contract ContractToModel(APIContractIn cin) {
		Contract res = new Contract();
		res.setCreditPeriod(cin.getCreditPeriod());
		res.setEndDate(cin.getEndDate());
		res.setNoticeDate(cin.getNoticeDate());
		res.setNumber(cin.getNumber());
		res.setPaymentMethod(cin.getPaymentMethod());
		res.setReviewDate(cin.getReviewDate());
		res.setStartDate(cin.getStartDate());
		res.setSupplier(cin.getSupplier());
		res.setTemplate(cin.getTemplate());
		res.setTitle(cin.getTitle());
		res.setDescription(cin.getDescription());
		return res;
	}

	public static APIContractOut ContractFromModel(Contract contract) {
		APIContractOut res = new APIContractOut();
		res.setEid(contract.getEid());
		return res;
	}
}
