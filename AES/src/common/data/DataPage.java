package common.data;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DataPage extends ArrayList<Record>{

	private long startIndex = 1;
	private long dbRecordCount = 0;
	
	public DataPage() {
		super();
	}
	
	public DataPage(long startIndex, long totalCount) {
		super();
		this.startIndex = startIndex;
		this.dbRecordCount = totalCount;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public long getDbRecordCount() {
		return dbRecordCount;
	}

	public void setDbRecordCount(long dbRecordCount) {
		this.dbRecordCount = dbRecordCount;
	}

	public List<List<String>> toList(){
		List<List<String>> rec = new ArrayList<List<String>>(this.size());
		for(Record r : this){
			rec.add(  r.list() );
		}
		return rec;
	}

	public void setStartIndex(int i) {
		startIndex = i;
		
	}

}
