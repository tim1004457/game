package com.pig8.api.platform.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 取序列
 * @author navy
 */
public class SeqUtils {
	// 文件每行的内容为
	// seq名称,currentValue(当前值),increment(步进值)
	public synchronized int getNextVal(String seq, String seqFileStr) {
		File seqFile = new File(seqFileStr);
		if (!seqFile.getParentFile().exists())
			seqFile.getParentFile().mkdirs();
		List<String> seqList = FileUtils.readFileLine(seqFile);
		List<String> tmpList = new ArrayList<String>();
		int nextValue = 0;
		boolean isExist = false;
		if (null != seqList && seqList.size() > 0) {
			for (String str : seqList) {
				if (null != str) {
					String[] seqs = str.split(",");
					String seqName = seqs[0];
					int currentValue = Integer.parseInt(seqs[1]);
					int increment = Integer.parseInt(seqs[2]);
					if (seqName.equals(seq)) {
						nextValue = currentValue + increment;
						currentValue = nextValue;
						isExist = true;
					}
					String tmpStr = seqName + "," + currentValue + ","
							+ increment;
					tmpList.add(tmpStr);
				}
			}
		}
		if (!isExist) {
			String tmpStr = seq + "," + 0 + "," + 1;
			tmpList.add(tmpStr);
		}
		FileUtils.writeFileLine(seqFile, tmpList);
		return nextValue;
	}
}
