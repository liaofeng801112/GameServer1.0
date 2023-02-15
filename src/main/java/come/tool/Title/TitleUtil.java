package come.tool.Title;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Titletable;

/**定时称谓判断*/
public class TitleUtil {
//	public static String[] titles=new String[]{"水陆大会·战神","种族赛·冠军"};
	public static String SL="水陆大会·战神";
	public static String LTS="擂台霸主";
    //角色称谓
    private static ConcurrentHashMap<BigDecimal,List<Titletable>> roleMap=new ConcurrentHashMap<>();
    //称谓集合
    private static ConcurrentHashMap<String,List<BigDecimal>> titleMap=new ConcurrentHashMap<>();
    /**添加称谓覆盖式添加  会先清空有这个称谓的人*/
    public static void addTitle(String title,BigDecimal... Ids){
    	List<BigDecimal> list=titleMap.remove(title);
    	if (list!=null) {
    		for (BigDecimal id:list) {
    			ClearTitle(title, id);
    		}
    		list.clear();
		}else {
			list=new ArrayList<>();
			titleMap.put(title, list);
		}
    	if (Ids!=null) {
    	   	for (int i = 0; i < Ids.length; i++) {
        		Titletable titletable=new Titletable(Ids[i],title);
        		list.add(Ids[i]);	
        		addTitle(titletable,Ids[i]);
    		}			
		} 
    }
    /**添加称谓 非覆盖式添加  不会先清空有这个称谓的人*/
    public static void addTitle2(String title,BigDecimal... Ids){
    	List<BigDecimal> list=titleMap.remove(title);
    	if (list==null) {
    		list=new ArrayList<>();
    		titleMap.put(title, list);
		}
    	for (int i = 0; i < Ids.length; i++) {
    		Titletable titletable=new Titletable(Ids[i],title);
    		list.add(Ids[i]);	
    		addTitle(titletable,Ids[i]);
		}
    }
    /**清除称谓*/
    public static void ClearTitle(String title,BigDecimal ID){
    	List<Titletable> list=roleMap.get(ID);
    	if (list!=null) {
			for (int i = list.size()-1; i >=0; i--) {
				Titletable titletable=list.get(i);
				if (titletable.getTitlename().equals(title)) {
					list.remove(titletable);
					break;
				}
			}
		}
    }
    /**清除称谓*/
    public static void addTitle(Titletable title,BigDecimal ID){
    	List<Titletable> list=roleMap.get(ID);
    	if (list==null) {
    		list=new ArrayList<>();
    		roleMap.put(ID, list);
		}
    	list.add(title);
    }
    /**补充称谓集合*/
    public static List<Titletable> getTitles(BigDecimal ID,List<Titletable> list){
    	List<Titletable> list2=roleMap.get(ID);
    	if (list2==null) {
    		return list;
		}
    	if (list==null) {
    		return list2;
		}
    	for (Titletable title:list2) {
    		list.add(title);
		}
		return list;	
    }
    /**判断是否能携带这个称谓*/
    public static boolean isTitle(String title,BigDecimal ID){
    	if (title==null) {
    		return true;
		}
    	List<BigDecimal> list=titleMap.get(title);
    	if (list==null) {
    		return true;
		}
    	if (list.contains(ID)) {
    		return true;
		}
		return false; 	
    }
}
