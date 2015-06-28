package com.pig8.api.platform.filter.word;


import com.pig8.api.platform.global.Global;
import com.pig8.api.platform.util.LogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author navy
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KeywordFilter extends PropertyPlaceholderConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger(getClass()); 
	
    // 1:最小长度匹配 2：最大长度匹配
    private int matchType = 1;

    /**
	 * 实例对象
	 */
	private static KeywordFilter instance = null; 
	
	/**
	 * 锁对象 线程安全
	 */
	private final static ReentrantLock lock = new ReentrantLock();
	
	/**
	 * 创建对象
	 * @return
	 */
	public static KeywordFilter getInstance(){
		if(instance == null){
			lock.lock();
				if(instance == null)
					instance = new KeywordFilter();
			lock.unlock();
		}
		
		return instance;
	}
	
	public String FilterWords(String txt){
		boolean boo = this.isContentKeyWords(txt);
		if(boo == true){
	        int l = txt.length();
	        for (int i = 0; i < l;)
	        {
	            int len = checkKeyWords(txt, i, matchType);
	            if (len > 0)
	            {
	                txt = txt.replaceAll(txt.substring(i, i + len),"*");
	                i += len;
	            } else
	            {
	                i++;
	            }
	        }
		}
        System.out.println(boo);
        System.out.println(txt);
		return txt;
	}
    /**
     * 初始化关键字列表
     */
    @Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		logger.info("************关键字库开始加载************");
		
		List<String> keywords = new ArrayList<String>();
		
        Enumeration<String> enu = (Enumeration<String>) props.propertyNames();
        while (enu.hasMoreElements())
        {
            try
            {
                String dd = (String) enu.nextElement();
                dd = new String(dd.getBytes("ISO8859-1"), "UTF-8");
                keywords.add(dd);
            } catch (UnsupportedEncodingException e)
            {
                LogUtils.error(e);
            }
        }
        
        addKeywords(keywords);
        logger.info("************关键字库加载结束************");
	}
    
    
    public void addKeywords(List<String> keywords)
    {
        for (int i = 0; i < keywords.size(); i++)
        {
            String key = keywords.get(i).trim();
            Map nowhash = null;
            nowhash = Global.FORBID_KEYS_MAP;
            for (int j = 0; j < key.length(); j++)
            {
                char word = key.charAt(j);
                Object wordMap = nowhash.get(word);
                if (wordMap != null)
                {
                    nowhash = (HashMap) wordMap;
                } else
                {
                    HashMap<String, String> newWordHash = new HashMap<String, String>();
                    newWordHash.put("isEnd", "0");
                    nowhash.put(word, newWordHash);
                    nowhash = newWordHash;
                }
                if (j == key.length() - 1)
                {
                    nowhash.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 重置关键词
     */
    public void clearKeywords()
    {
    	Global.FORBID_KEYS_MAP.clear();
    }

    /**
     * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
     * flag 1:最小长度匹配 2：最大长度匹配
     */
    private int checkKeyWords(String txt, int begin, int flag)
    {
    	Map nowhash = null;
        nowhash = Global.FORBID_KEYS_MAP;
        int maxMatchRes = 0;
        int res = 0;
        int l = txt.length();
        char word = 0;
        for (int i = begin; i < l; i++)
        {
            word = txt.charAt(i);
            Object wordMap = nowhash.get(word);
            if (wordMap != null)
            {
                res++;
                nowhash = (HashMap) wordMap;
                if (((String) nowhash.get("isEnd")).equals("1"))
                {
                    if (flag == 1)
                    {
                        wordMap = null;
                        nowhash = null;
                        txt = null;
                        return res;
                    } else
                    {
                        maxMatchRes = res;
                    }
                }
            } else
            {
                txt = null;
                nowhash = null;
                return maxMatchRes;
            }
        }
        txt = null;
        nowhash = null;
        return maxMatchRes;
    }

    /**
     * 返回txt中关键字的列表
     */
    public List<String> getTxtKeyWords(String txt)
    {
        List<String> list = new ArrayList<String>();
        int l = txt.length();
        for (int i = 0; i < l;)
        {
            int len = checkKeyWords(txt, i, matchType);
            if (len > 0)
            {
                String tt = "'" + txt.substring(i, i + len) + "'";
                txt = txt.replaceAll(txt.substring(i, i + len),"*");
                list.add(tt);
                i += len;
            } else
            {
                i++;
            }
        }
        System.out.println(txt);
        txt = null;
        return list;
    }

    /**
     * 仅判断txt中是否有关键字
     */
    public boolean isContentKeyWords(String txt)
    {
        for (int i = 0; i < txt.length(); i++)
        {
            int len = checkKeyWords(txt, i, 1);
            if (len > 0)
            {
                return true;
            }
        }
        txt = null;
        return false;
    }
    
    /**
     * 初始化敏感词列表
     * */
    public void initfiltercode()
    {
        List<String> keywords = new ArrayList<String>();

        InputStream in = KeywordFilter.class.getClassLoader().getResourceAsStream("words.properties");
        Properties pro = new Properties();
        try
        {
            pro.load(in);
            in.close();
        } catch (IOException e1)
        {
            LogUtils.error(e1);
        }
        Enumeration<String> enu = (Enumeration<String>) pro.propertyNames();
        while (enu.hasMoreElements())
        {
            try
            {
                String dd = (String) enu.nextElement();
                dd = new String(dd.getBytes("ISO8859-1"), "UTF-8");
                keywords.add(dd);
            } catch (UnsupportedEncodingException e)
            {
                LogUtils.error(e);
            }
        }
        addKeywords(keywords);
    }
    
    public int getMatchType()
    {
        return matchType;
    }

    public void setMatchType(int matchType)
    {
        this.matchType = matchType;
    }

    public static void main(String[] args) throws IOException
    {
    	KeywordFilter filter = new KeywordFilter();
        filter.initfiltercode();
        Date date = new Date();
        String txt = "叫晶晶的女孩……”长孙湘雨荡尽天下闻言忍俊不禁，用手中的折扇掩着嘴，布卖淫女止不住地笑了起来，直到谢安脸上不渝的表情越来越明显，她这才逐渐收起笑意，轻笑着说道，“呐，舞姐姐本来就是做事细致的人，似你等懒散，她瞧得过去才怪！——更别说你还背着她到城里的青楼吃酒，与里面的女子亲亲我我，奴家真是纳闷，舞姐姐那日怎么就没有当场斩了你呢！”喜欢就好，”伊伊甜蜜一笑，替谢安盛了一碗饭递给他，随即纳闷问道，“安，说起来，奴家方才前前后后找过，你这屋子里，锅碗瓢盆什么都没有，害得我还再回府一趟，将厨灶所需的那些东西带了来……奴家很纳闷，安，你平日里究竟是如何做饭做菜的呢？”需要过滤的内容南京大屠杀，89学潮血腥屠杀舞，你别总是这样好不好？她向我保证过，礼部尚书、礼部侍郎都是她祖父的门生，望着谢安恳求的目光，梁丘舞幽幽叹了口气，摇头说道，“你还是不明白，安，我不是怕你犯下这欺君之罪，而是怕你受那个女人摆布，她在想办法控制你，安！舞，我很感激你当初不计前嫌，也不计较身份与地位的差距，与我这一介平民成婚，并且，对我的照顾也是极为细致，只不过……我算是你丈夫吧？我不是你的孩子，也不想当你要教育的对象，你完全没有必要替我安排所有的事，我有我自己的主观判断能力……”——只要你接受了，日后就有个把柄落在她手上……”其余官员，也多受她长孙家提携恩情，FL大法不会有人去追查这件事的，中南海权力斗争就一次，就一次，好吗？";
        boolean boo = filter.isContentKeyWords(txt);
        System.out.println(boo);
        List<String> set = filter.getTxtKeyWords(txt);
        System.out.println("包含的敏感词如下："+set);
        Date date2 = new Date();
        float ss = date2.getTime() - date.getTime();
        System.out.println(ss + "毫秒");
    }
}