package mapper;

import java.util.List;
import java.util.Map;

public interface ItemMapper {
	void addItems(List items);
	void addTxqk(Map txqk);
	void updateTxqk(Map txqk);
	List getItemDic(String tablename);
	List getDeparts(int sjks);
	List getFuncDeparts(String bz);
	int generateLoginId(); 
	String getTbName(String tablename);
	Map getLoginFlag(String jlxh);
	void addLoginFlag(int loginId);
	int getZy(int ksdm);

}
