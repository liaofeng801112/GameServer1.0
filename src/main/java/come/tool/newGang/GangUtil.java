package come.tool.newGang;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Gang;
import org.come.until.AllServiceUtil;

public class GangUtil {
	
	private static ConcurrentHashMap<BigDecimal,GangDomain> gangMap;
	private static List<Gang> gangs;
	
	public static void init(){
		gangMap=new ConcurrentHashMap<>();
		gangs = AllServiceUtil.getGangService().findAllGang();
        for (int i = 0, length = gangs.size(); i < length; i++) {
			Gang gang=gangs.get(i);
			gangMap.put(gang.getGangid(), new GangDomain(gang));
		}
	}
	/**获取*/
	public static GangDomain getGangDomain(BigDecimal gangId){
		return gangMap.get(gangId);
	}
	public static Gang getGang(BigDecimal gangId){
		GangDomain gangDomain=gangMap.get(gangId);
		return gangDomain!=null?gangDomain.getGang():null;
	}
	/**添加*/
	public static GangDomain addGangDomain(Gang gang,BigDecimal roleId,ChannelHandlerContext ctx){
		GangDomain domain=new GangDomain(gang);
		gangMap.put(gang.getGangid(), domain);
		domain.upGangRole(roleId, ctx);
		gangs.add(gang);
		return domain;
	}
	/**保存*/
	public static void upGangs(boolean is){
		Iterator<GangDomain>  iterGang=gangMap.values().iterator();
        while (iterGang.hasNext()){
        	GangDomain gangDomain=iterGang.next();
        	if (is) {
        		gangDomain.upXY();
			}
        	gangDomain.upGang();
        }
	}
	public static List<Gang> getGangs() {
		return gangs;
	}
	public static void setGangs(List<Gang> gangs) {
		GangUtil.gangs = gangs;
	}
	
}
