package common.data;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Record extends ArrayList<String>{

	public Record(){
		super();
	}
	
	List<String> list(){
		List<String> rec = new ArrayList<String>(this.size());
		for(String s : this){
			rec.add(s);
		}
		return rec;
	}

}
