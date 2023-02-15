package come.tool.FightingData;

/**
 * 
 * @author Administrator
 *
 *角色抗性字段
 */

public class Ql implements Cloneable{
	    //抗性
		private double kwl=0;//抗物理
		private double kzs=0;//抗震慑
		private double kff=0;//抗风
		private double klf=0;//抗雷
		private double ksf=0;//坑水
		private double khf=0;//坑火
		private double khl=0;//抗混乱
		private double khs=0;//抗昏睡
		private double kfy=0;//抗封印
		private double kzd=0;//抗中毒
		private double kzds=0;//抗中毒伤害
		private double kyw=0;//抗遗忘
		private double kgh=0;//抗鬼火
		private double ksc=0;//抗三尸
		private double klb;//抗灵宝伤害
		private double kqk;//抵御强克效果
		private double kwsx;//抗无属性伤害
		private double kzshp;//抗震慑气血
		private double kzsmp;//抗震慑魔法
		private double kjge;//抗金箍
		private double kqw;//抗情网
		private double khr;//抗浩然正气
		private double kqm;//抗青面獠牙
		private double ktm;//抗天魔解体
		private double kxl;//抗小楼夜哭
		private double kfg;//抗分光化影
		private double kzml;//抗致命率
		private double kbf;//抗风法狂暴
		private double kbh;//抗火法狂暴
		private double kbs;//抗水法狂暴
		private double kbl;//抗雷法狂暴
		private double kbg;//抗鬼火狂暴
		//忽视
		private double hfyv=0;//忽视防御程度
		private double hfyl=0;//忽视防御几率
		private double hff=0;//忽视风法
		private double hlf=0;//忽视雷法
		private double hsf=0;//忽视水法
		private double hhf=0;//忽视火法
		private double hhl=0;//忽视混乱
		private double hhs=0;//忽视昏睡
		private double hfy=0;//忽视封印
		private double hzd=0;//忽视中毒
		private double hzs=0;//忽视抗震慑
	    private double hds=0;//忽视躲闪
	    private double hfj=0;//忽视反击
	    private double hxfkl=0;//忽视仙法抗性率
	    private double hxfcd=0;//忽视仙法抗性程度
		private double hgh=0;//忽视鬼火
		private double hyw=0;//忽视遗忘
		
		//强
		private double qff=0;//强风法	
		private double qlf=0;//强雷法
		private double qsf=0;//强水法
		private double qhf=0;//强火法
		private double qhl=0;//强混乱
		private double qhs=0;//强昏睡
		private double qzs=0;//强震慑
		private double qfy=0;//强封印
		private double qzd=0;//强中毒
		private double qzds=0;//强中毒伤害
		private double qgh=0;//强鬼火
		private double qyw=0;//强遗忘
		private double qsc=0;//强三尸血
		private double qschx=0;//强三尸血回血程度
		private double qqk;//增加强克效果
		private double qzhs;//对召唤兽伤害
		private double qgjf=0;//加强攻击法术效果
		private double qfyf=0;//加强防御法术效果
		private double qsdf=0;//加强速度法术效果
		private double qmh=0;//加强魅惑
		private double qjg=0;//强金箍
		private double qqw=0;//强情网
		
		private double qlpl=0;//加强霹雳效果
		private double qlfy=0;//加强扶摇效果
		private double qlcb=0;//加强沧波效果
		private double qlglv=0;//加强甘霖回血值
		private double qlglc=0;//加强甘霖回血程度
		//其他
		private double eds=0;//躲闪率
		private double efjl=0;//反击率
		private double efjv=0;//反击次数
		private double eljl=0;//连击率
		private double eljv=0;//连击次数	
		private double emzl=0;//命中率
		private double ezml=0;//致命率
		private double ekbl=0;//狂暴率
		private double efzl=0;//反震率
		private double efzcd=0;//反震程度
	    private double exfljl=0;//仙法连击率
	    private double exfljs=0;//仙法连击次数
	    
	    private double ezs=0;//百分比增伤
	    private double ewlzs=0;//百分比物理增伤
	    private double ejs=0;//百分比减伤
	    private double exfjs=0;//仙法百分比减伤
	    private double ewljs=0;//物理百分比减伤
	    private double ezsjs=0;//震慑百分比减伤
	    private double efsds=0;//法术躲闪
		
	    
	    //五行
		private double wxj=0;//金
		private double wxm=0;//木
		private double wxt=0;//土
		private double wxs=0;//水	
		private double wxh=0;//火
		private double wxqj=0;//强力克金
		private double wxqm=0;//强力克木
		private double wxqt=0;//强力克土
		private double wxqs=0;//强力克水
		private double wxqh=0;//强力克火
		
		//伤害
		private double swsx=0;//无属性伤害
		private double sff=0;//风法伤害
		private double slf=0;//雷法伤害	
		private double ssf=0;//水法伤害
		private double shf=0;//火法伤害
		private double szd=0;//毒伤害
		private double sgh=0;//鬼火伤害
		private double ssc=0;//三尸伤害
	    
		//狂暴
		private double blf=0;//雷法狂暴
		private double bff=0;//风法狂暴
		private double bsf=0;//水法狂暴
		private double bhf=0;//火法狂暴
	    private double bgh=0;//鬼火狂暴
	    private double bsc=0;//三尸虫狂暴
	    private double bfy=0;//封印狂暴
	    private double bhl=0;//混乱狂暴
	    private double bhs=0;//昏睡狂暴
	    private double bzd=0;//毒法狂暴
	    private double bjf=0;//加防狂暴
	    private double bjg=0;//加攻狂暴
	    private double bzs=0;//震慑狂暴
	    private double byw=0;//遗忘狂暴
	    private double bmh=0;//魅惑狂暴
	    
	    private double blfcd=0;//雷法狂暴程度
		private double bffcd=0;//风法狂暴程度
		private double bsfcd=0;//水法狂暴程度
		private double bhfcd=0;//火法狂暴程度
	    private double bghcd=0;//鬼火狂暴程度
	    private double bsccd=0;//三尸虫狂暴程度
	    
		private double f_f;//附加封印攻击
		private double f_h;//附加混乱攻击
		private double f_d;//附加中毒攻击
		private double f_xf;//附加风法攻击
		private double f_xh;//附加火法攻击
		private double f_xs;//附加水法攻击
		private double f_xl;//附加雷法攻击
		private double f_zs;//附加震慑攻击
		private double f_sc;//附加三尸攻击	
		
		//躲闪
		private double dzs;//震慑躲闪
		private double dhf;//火法躲闪
		private double dlf;//雷法躲闪
		private double dff;//风法躲闪
		private double dsf;//水法躲闪
		private double ddf;//毒法躲闪
		private double dfy;//封印躲闪
		private double dhl;//混乱躲闪
		private double dhs;//昏睡躲闪
		private double dyw;//遗忘躲闪
		private double dgh;//鬼火躲闪
		private double dsc;//三尸虫躲闪
		
		//伤害减免
		private double jsf;//水法伤害减免
		private double jff;//风法伤害减免
		private double jlf;//雷法伤害减免
		private double jhf;//火法伤害减免
		private double jgh;//鬼火伤害减免

		public Ql() {
			// TODO Auto-generated constructor stub
		}

		/**忽视全系*/
		public void addHS(double v){
			hff+=v;
			hlf+=v;
			hsf+=v;
			hhf+=v;
			hhl+=v;
			hhs+=v;
			hzd+=v;
			hzs+=v;
			hds+=v;
			hgh+=v;
			hyw+=v;
		}
		/**加强全系法术*/
		public void addQ(double v){
			qff+=v;
			qlf+=v;
			qsf+=v;
			qhf+=v;
			
			qhl+=v;
			qhs+=v;
			qfy+=v;
			qzds+=v;
			
			qzs+=v;
			qgjf+=v;
			qfyf+=v;
			qsdf+=v;
			
			qgh+=v;
			qyw+=v;
			qsc+=v*100;
			qmh+=v;
			
			qlpl+=v;
			qlfy+=v;
			qlcb+=v;
			qlglv+=v*100;
		}
		/**控制法4抗上限过滤*/
		public void addKKUp(double up){
			if (khl>up) {khl=up;}
			if (khs>up) {khs=up;}
			if (kfy>up) {kfy=up;}
			if (kyw>up) {kyw=up;}
		}
		/**抗上限过滤*/
		public void addKUp(double up){
			if (kwl>up) {kwl=up;}
			if (kzs>up) {kzs=up;}
			if (kff>up) {kff=up;}
			if (klf>up) {klf=up;}
			if (ksf>up) {ksf=up;}
			if (khf>up) {khf=up;}
			if (kzd>up) {kzd=up;}
			if (kgh>up) {kgh=up;}
			if (klb>up) {klb=up;}
			if (kzml>up) {kzml=up;}
		}
		/**附加攻击判断*/
		public double MFj(){
			double size=f_f+f_h+f_d+f_xf+f_xh+f_xs+f_xl+f_zs+f_sc;
			if (size>=300) {
				this.f_f=0;this.f_h=0;this.f_d=0;
				this.f_xf=0;this.f_xh=0;this.f_xs=0;
				this.f_xl=0;this.f_zs=0;this.f_sc=0;	
			}
			return size;
		}
		/**加强判断*/
		public boolean MJq(){
			if (qff>200) {if (qff>300) {return true;}qff=200;}
			if (qlf>200) {if (qlf>300) {return true;}qlf=200;}
			if (qsf>200) {if (qsf>300) {return true;}qsf=200;}
			if (qhf>200) {if (qhf>300) {return true;}qhf=200;}
			if (qgh>200) {if (qgh>300) {return true;}qgh=200;}
			
			if (qhl>200) {if (qhl>300) {return true;}qhl=200;}
			if (qhs>200) {if (qhs>300) {return true;}qhs=200;}
			if (qzs>200) {if (qzs>300) {return true;}qzs=200;}
			if (qfy>200) {if (qfy>300) {return true;}qfy=200;}
			if (qzd>200) {if (qzd>300) {return true;}qzd=200;}
			if (qyw>200) {if (qyw>300) {return true;}qyw=200;}
			if (qzds>300) {if (qzds>400) {return true;}qzds=300;}
			
			if (qqk>100) {if (qqk>150) {return true;}qzds=100;}

			if (qsc>25000) {if (qsc>35000) {return true;}qsc=25000;}
			
			if (qlpl>125) {if (qlpl>200) {return true;}qlpl=125;}
			if (qlfy>125) {if (qlfy>200) {return true;}qlfy=125;}
			if (qlcb>125) {if (qlcb>200) {return true;}qlcb=125;}
			
			return false;
		}
		/**忽视判断*/
		public boolean MHs(){
			
			if (hfyv>150) {if (hfyv>200) {return true;}hfyv=150;}

			if (hff>200) {if (hff>250) {return true;}hff=200;}
			if (hlf>200) {if (hlf>250) {return true;}hlf=200;}
			if (hsf>200) {if (hsf>250) {return true;}hsf=200;}
			if (hhf>200) {if (hhf>250) {return true;}hhf=200;}
			if (hgh>200) {if (hgh>250) {return true;}hgh=180;}
			if (hxfcd>200) {if (hxfcd>250) {return true;}hxfcd=180;}
			
			if (hzs>15) {if (hzs>30) {return true;}hzs=15;}
            return false;
		}
		/**额外判断*/
		public boolean MEw(){
			
			if (swsx>100) {if (swsx>150) {return true;}swsx=100;}
			
			if (ssc>25000) {if (ssc>35000) {return true;}ssc=25000;}
			if (szd>25000) {if (szd>35000) {return true;}szd=25000;}
			
			if (sff>15000) {if (sff>25000) {return true;}sff=15000;}
			if (slf>15000) {if (slf>25000) {return true;}slf=15000;}
			if (ssf>15000) {if (ssf>25000) {return true;}ssf=15000;}
			if (shf>15000) {if (shf>25000) {return true;}shf=15000;}
			if (sgh>15000) {if (sgh>25000) {return true;}sgh=15000;}
			
			if (blfcd>70) {if (blfcd>100) {return true;}blfcd=70;}
			if (bffcd>70) {if (bffcd>100) {return true;}bffcd=70;}
			if (bsfcd>70) {if (bsfcd>100) {return true;}bsfcd=70;}
			if (bhfcd>70) {if (bhfcd>100) {return true;}bhfcd=70;}
			if (bghcd>70) {if (bghcd>100) {return true;}bghcd=70;}
			if (bsccd>70) {if (bsccd>100) {return true;}bsccd=70;}

			if (exfljs>10) {if (exfljs>15) {return true;}exfljs=10;}

			if (eljv>15) {if (eljv>25) {return true;}eljv=15;}
			return false;
		}
		/**五行判断*/
		public boolean MWx(){
			if (wxj>100) {if (wxj>200) {return true;}wxj=100;}
			if (wxm>100) {if (wxm>200) {return true;}wxm=100;}
			if (wxt>100) {if (wxt>200) {return true;}wxt=100;}
			if (wxs>100) {if (wxs>200) {return true;}wxs=100;}
			if (wxh>100) {if (wxh>200) {return true;}wxh=100;}
			
			if (wxqj>400) {if (wxqj>500) {return true;}wxqj=400;}
			if (wxqm>400) {if (wxqm>500) {return true;}wxqm=400;}
			if (wxqt>400) {if (wxqt>500) {return true;}wxqt=400;}
			if (wxqs>400) {if (wxqs>500) {return true;}wxqs=400;}
			if (wxqh>1000) {if (wxqh>1500) {return true;}wxqh=1000;}
			
			return false;
		}
		/**抗性判断*/
		public boolean MKx(){
			if (kwl>200) {if (kwl>300) {return true;}kwl=200;}
			if (kzs>100) {if (kzs>200) {return true;}kzs=100;}
			if (kff>150) {if (kff>250) {return true;}kff=150;}
			if (klf>150) {if (klf>250) {return true;}klf=150;}
			if (ksf>150) {if (ksf>250) {return true;}ksf=150;}
			if (khf>150) {if (khf>250) {return true;}khf=150;}
			if (kgh>150) {if (kgh>250) {return true;}kgh=150;}

			if (khl>200) {if (khl>250) {return true;}khl=200;}
			if (khs>200) {if (khs>250) {return true;}khs=200;}
			if (kfy>200) {if (kfy>250) {return true;}kfy=200;}
			if (kzd>200) {if (kzd>250) {return true;}kzd=200;}
			if (kyw>200) {if (kyw>250) {return true;}kyw=200;}

			if (ksc>15000) {if (ksc>25000) {return true;}ksc=15000;}
			return false;
		}
		/**重置*/
		public void Reset(){
			kwl=0;kzs=0;kff=0;klf=0;ksf=0;khf=0;khl=0;khs=0;kfy=0;
			kzd=0;kzds=0;kyw=0;kgh=0;ksc=0;klb=0;kqk=0;kwsx=0;kzshp=0;
			kzsmp=0;kjge=0;kqw=0;khr=0;kqm=0;ktm=0;kxl=0;kfg=0;kzml=0;
			
			hfyv=0;hfyl=0;hff=0;hlf=0;hsf=0;hhf=0;
			hhl=0;hhs=0;hfy=0;hzd=0;hzs=0;hds=0;
			hfj=0;hxfkl=0;hxfcd=0;hgh=0;hyw=0;
		    
			qff=0;qlf=0;qsf=0;qhf=0;qhl=0;qhs=0;qzs=0;qfy=0;qzd=0;qzds=0;
			qgh=0;qyw=0;qsc=0;qschx=0;qqk=0;qzhs=0;qgjf=0;qfyf=0;qsdf=0;
			qlcb=0;qlpl=0;qlglc=0;qlglv=0;qlfy=0;
			
			eds=0;efjl=0;efjv=0;eljl=0;eljv=0;emzl=0;ezml=0;
			ekbl=0;efzl=0;efzcd=0;exfljl=0;exfljs=0;
			
			wxj=0;wxm=0;wxt=0;wxs=0;wxh=0;wxqj=0;wxqm=0;wxqt=0;wxqs=0;wxqh=0;
			
			swsx=0;sff=0;slf=0;ssf=0;shf=0;szd=0;sgh=0;ssc=0;
			
			blf=0;bff=0;bsf=0;bhf=0;bgh=0;bsc=0;
			blfcd=0;bffcd=0;bsfcd=0;bhfcd=0;bghcd=0;bsccd=0;
			
			f_f=0;f_h=0;f_d=0;f_xf=0;f_xh=0;f_xs=0;f_xl=0;
			f_zs=0;f_sc=0;
			
			dzs=0;dhf=0;dlf=0;dff=0;dsf=0;ddf=0;dfy=0;dhl=0;dhs=0;dgh=0;dyw=0;dsc=0;
			jsf=0;jff=0;jlf=0;jlf=0;jgh=0;
		}
		/**抗人法*/
		public void addkr(double v){
			 khl+=v;
			 khs+=v; 
			 kfy+=v;
			 kzd+=v;
		}
		/**抗伤害法*/
		public void addks(double v){
			 kff+=v;
			 khf+=v; 
			 klf+=v;
			 ksf+=v;
			 kgh+=v;	 
		}
		/**抗性群加*/
		public void addkang(int v){
		    kwl+=v/2;
		    kzs+=v/2;
			kff+=v;
			klf+=v;
			ksf+=v;
			khf+=v;
			kgh+=v;
			ksc+=v*50;
		}
		/**强物理*/
		public void addap(int v){
			hfyv+=v;
			hfyl+=v;
			eljl+=v;
			eljv+=5;
			emzl+=v;
			ekbl+=v/2;
		}
		//狂暴处理
		public void kuangbao(int v){
			ekbl+=100*v;
			bgh+=100*v;
			bsc+=100*v;
			blf+=100*v;
			bff+=100*v;
			bsf+=100*v;
			bhf+=100*v;
			sff+=1500*v;
			slf+=1500*v;
			ssf+=1500*v;
			shf+=1500*v;
			szd+=1500*v;
			sgh+=1500*v;
			ssc+=1500*v;
		}
		/**抗风火水雷鬼*/
		public void addK_FHSLG(double v){
			 kff+=v;khf+=v; 
			 ksf+=v;klf+=v;
			 kgh+=v;
		}
		/**抗冰混睡遗*/
		public void addK_BHSY(double v){
			 khl+=v;khs+=v; 
			 kfy+=v;kyw+=v;
		}
		/**师门系列对比*/
		public boolean isSMXL(int v1,int v2){
			return getSMXL(v1)>=getSMXL(v2);
		}
		public double getSMXL(int v){
			if (v==0) {//混乱
				return qhl+hhl*2;
			}else if (v==1) {//封印
				return qfy+hfy*2;
			}else if (v==2) {//昏睡
				return qhs+hhs*2;
			}else if (v==3) {//中毒
				return qzd+hzd*2+qzds;
			}else if (v==4) {//震慑
				return qzs+hzs*2;
			}else if (v==5) {//力量
				return qgjf;
			}else if (v==6) {//抗性
				return qfyf;
			}else if (v==7) {//加速
				return qsdf;
			}else if (v==8) {//风
				return qff+hff*2;
			}else if (v==9) {//雷
				return qlf+hlf*2;
			}else if (v==10) {//水
				return qsf+hsf*2;
			}else if (v==11) {//火 
				return qhf+hhf*2;
			}else if (v==12) {//鬼火
				return qgh+hgh*2;
			}else if (v==13) {//三尸
				return (qsc+ssc)/100;
			}else if (v==14) {//遗忘
				return qyw+hyw*2;
			}else if (v==15) {//魅惑
				return qmh+qfyf;
			}else if (v==16) {//霹雳
				return qlpl;
			}else if (v==17) {//沧波
				return qlcb;
			}else if (v==18) {//甘霖
				return qlglv/150;
			}else if (v==19) {//扶摇
				return qlfy;
			}
			return 0;
		}
		public double getRolekwl() {
			return kwl;
		}

		public void setRolekwl(double rolekwl) {
			this.kwl = rolekwl;
		}

		public double getRolekzs() {
			return kzs;
		}

		public void setRolekzs(double rolekzs) {
			this.kzs = rolekzs;
		}

		public double getRolekff() {
			return kff;
		}

		public void setRolekff(double rolekff) {
			this.kff = rolekff;
		}

		public double getRoleklf() {
			return klf;
		}

		public void setRoleklf(double roleklf) {
			this.klf = roleklf;
		}

		public double getRoleksf() {
			return ksf;
		}

		public void setRoleksf(double roleksf) {
			this.ksf = roleksf;
		}

		public double getRolekhf() {
			return khf;
		}

		public void setRolekhf(double rolekhf) {
			this.khf = rolekhf;
		}

		public double getRolekhl() {
			return khl;
		}

		public void setRolekhl(double rolekhl) {
			this.khl = rolekhl;
		}

		public double getRolekhs() {
			return khs;
		}

		public void setRolekhs(double rolekhs) {
			this.khs = rolekhs;
		}

		public double getRolekfy() {
			return kfy;
		}

		public void setRolekfy(double rolekfy) {
			this.kfy = rolekfy;
		}

		public double getRolekzd() {
			return kzd;
		}

		public void setRolekzd(double rolekzd) {
			this.kzd = rolekzd;
		}

		public double getRolekyw() {
			return kyw;
		}

		public void setRolekyw(double rolekyw) {
			this.kyw = rolekyw;
		}

		public double getRolekgh() {
			return kgh;
		}

		public void setRolekgh(double rolekgh) {
			this.kgh = rolekgh;
		}

		public double getRoleksc() {
			return ksc;
		}

		public void setRoleksc(double roleksc) {
			this.ksc = roleksc;
		}

		public double getRolehsfyv() {
			return hfyv;
		}

		public void setRolehsfyv(double rolehsfyv) {
			this.hfyv = rolehsfyv;
		}

		public double getRolehsfyl() {
			return hfyl;
		}

		public void setRolehsfyl(double rolehsfyl) {
			this.hfyl = rolehsfyl;
		}

		public double getRolehsff() {
			return hff;
		}

		public void setRolehsff(double rolehsff) {
			this.hff = rolehsff;
		}

		public double getRolehslf() {
			return hlf;
		}

		public void setRolehslf(double rolehslf) {
			this.hlf = rolehslf;
		}

		public double getRolehssf() {
			return hsf;
		}

		public void setRolehssf(double rolehssf) {
			this.hsf = rolehssf;
		}

		public double getRolehshf() {
			return hhf;
		}

		public void setRolehshf(double rolehshf) {
			this.hhf = rolehshf;
		}

		public double getRolehshl() {
			return hhl;
		}

		public void setRolehshl(double rolehshl) {
			this.hhl = rolehshl;
		}

		public double getRolehshs() {
			return hhs;
		}

		public void setRolehshs(double rolehshs) {
			this.hhs = rolehshs;
		}

		public double getRolehsfy() {
			return hfy;
		}

		public void setRolehsfy(double rolehsfy) {
			this.hfy = rolehsfy;
		}

		public double getRolehszd() {
			return hzd;
		}

		public void setRolehszd(double rolehszd) {
			this.hzd = rolehszd;
		}

		public double getRoleqff() {
			return qff;
		}

		public void setRoleqff(double roleqff) {
			this.qff = roleqff;
		}

		public double getRoleqlf() {
			return qlf;
		}

		public void setRoleqlf(double roleqlf) {
			this.qlf = roleqlf;
		}

		public double getRoleqsf() {
			return qsf;
		}

		public void setRoleqsf(double roleqsf) {
			this.qsf = roleqsf;
		}
		public double getRoleqhf() {
			return qhf;
		}
		public void setRoleqhf(double roleqhf) {
			this.qhf = roleqhf;
		}
		public double getRoleqhl() {
			return qhl;
		}
		public void setRoleqhl(double roleqhl) {
			this.qhl = roleqhl;
		}
		public double getRoleqhs() {
			return qhs;
		}
		public void setRoleqhs(double roleqhs) {
			this.qhs = roleqhs;
		}
		public double getRoleqzs() {
			return qzs;
		}

		public void setRoleqzs(double roleqzs) {
			this.qzs = roleqzs;
		}

		public double getRoleqfy() {
			return qfy;
		}

		public void setRoleqfy(double roleqfy) {
			this.qfy = roleqfy;
		}

		public double getRoleqzd() {
			return qzd;
		}

		public void setRoleqzd(double roleqzd) {
			this.qzd = roleqzd;
		}

		public double getRolefdsl() {
			return eds;
		}

		public void setRolefdsl(double rolefdsl) {
			this.eds = rolefdsl;
		}

		public double getRoleffjl() {
			return efjl;
		}

		public void setRoleffjl(double roleffjl) {
			this.efjl = roleffjl;
		}

		public double getRoleffjv() {
			return efjv;
		}

		public void setRoleffjv(double roleffjv) {
			this.efjv = roleffjv;
		}

		public double getRolefljl() {
			return eljl;
		}

		public void setRolefljl(double rolefljl) {
			this.eljl = rolefljl;
		}

		public double getRolefljv() {
			return eljv;
		}

		public void setRolefljv(double rolefljv) {
			this.eljv = rolefljv;
		}

		public double getRolefmzl() {
			return emzl;
		}

		public void setRolefmzl(double rolefmzl) {
			this.emzl = rolefmzl;
		}

		public double getRolefkbl() {
			return ekbl;
		}
		public void setRolefkbl(double rolefkbl) {
			this.ekbl = rolefkbl;
		}
		public double getRoleffzl() {
			return efzl;
		}
		public void setRoleffzl(double roleffzl) {
			this.efzl = roleffzl;
		}
		public double getRoleffzcd() {
			return efzcd;
		}
		public void setRoleffzcd(double roleffzcd) {
			this.efzcd = roleffzcd;
		}
		public double getRolewxj() {
			return wxj;
		}

		public void setRolewxj(double rolewxj) {
			this.wxj = rolewxj;
		}

		public double getRolewxm() {
			return wxm;
		}

		public void setRolewxm(double rolewxm) {
			this.wxm = rolewxm;
		}

		public double getRolewxt() {
			return wxt;
		}

		public void setRolewxt(double rolewxt) {
			this.wxt = rolewxt;
		}

		public double getRolewxs() {
			return wxs;
		}

		public void setRolewxs(double rolewxs) {
			this.wxs = rolewxs;
		}

		public double getRolewxh() {
			return wxh;
		}

		public void setRolewxh(double rolewxh) {
			this.wxh = rolewxh;
		}

		public double getRolewxqkj() {
			return wxqj;
		}

		public void setRolewxqkj(double rolewxqkj) {
			this.wxqj = rolewxqkj;
		}

		public double getRolewxqkm() {
			return wxqm;
		}

		public void setRolewxqkm(double rolewxqkm) {
			this.wxqm = rolewxqkm;
		}

		public double getRolewxqkt() {
			return wxqt;
		}

		public void setRolewxqkt(double rolewxqkt) {
			this.wxqt = rolewxqkt;
		}

		public double getRolewxqks() {
			return wxqs;
		}

		public void setRolewxqks(double rolewxqks) {
			this.wxqs = rolewxqks;
		}

		public double getRolewxqkh() {
			return wxqh;
		}

		public void setRolewxqkh(double rolewxqkh) {
			this.wxqh = rolewxqkh;
		}

		public double getRolewsxsh() {
			return swsx;
		}

		public void setRolewsxsh(double rolewsxsh) {
			this.swsx = rolewsxsh;
		}

		public double getRoleffsh() {
			return sff;
		}

		public void setRoleffsh(double roleffsh) {
			this.sff = roleffsh;
		}

		public double getRolelfsh() {
			return slf;
		}

		public void setRolelfsh(double rolelfsh) {
			this.slf = rolelfsh;
		}

		public double getRolesfsh() {
			return ssf;
		}

		public void setRolesfsh(double rolesfsh) {
			this.ssf = rolesfsh;
		}

		public double getRolehfsh() {
			return shf;
		}

		public void setRolehfsh(double rolehfsh) {
			this.shf = rolehfsh;
		}

		public double getRolelfkb() {
			return blf;
		}

		public void setRolelfkb(double rolelfkb) {
			this.blf = rolelfkb;
		}

		public double getRoleffkb() {
			return bff;
		}

		public void setRoleffkb(double roleffkb) {
			this.bff = roleffkb;
		}

		public double getRolesfkb() {
			return bsf;
		}

		public void setRolesfkb(double rolesfkb) {
			this.bsf = rolesfkb;
		}

		public double getRolehfkb() {
			return bhf;
		}

		public void setRolehfkb(double rolehfkb) {
			this.bhf = rolehfkb;
		}

		public double getRolezdsh() {
			return szd;
		}

		public void setRolezdsh(double rolezdsh) {
			this.szd = rolezdsh;
		}

		public double getRoleghsh() {
			return sgh;
		}

		public void setRoleghsh(double roleghsh) {
			this.sgh = roleghsh;
		}

		public double getRolesssh() {
			return ssc;
		}

		public void setRolesssh(double rolesssh) {
			this.ssc = rolesssh;
		}

		public double getRolegstronghostfire() {
			return qgh;
		}

		public void setRolegstronghostfire(double rolegstronghostfire) {
			this.qgh = rolegstronghostfire;
		}

		public double getRolestrongforget() {
			return qyw;
		}

		public void setRolestrongforget(double rolestrongforget) {
			this.qyw = rolestrongforget;
		}

		public double getRolestrongbodyblood() {
			return qsc;
		}

		public void setRolestrongbodyblood(double rolestrongbodyblood) {
			this.qsc = rolestrongbodyblood;
		}

		public double getRolestrongbodyblooddeep() {
			return qschx;
		}

		public void setRolestrongbodyblooddeep(double rolestrongbodyblooddeep) {
			this.qschx = rolestrongbodyblooddeep;
		}

		public double getRoleghkb() {
			return bgh;
		}

		public void setRoleghkb(double roleghkb) {
			this.bgh = roleghkb;
		}

		public double getRolesskb() {
			return bsc;
		}

		public void setRolesskb(double rolesskb) {
			this.bsc = rolesskb;
		}

		public double getRolehsds() {
			return hds;
		}

		public void setRolehsds(double rolehsds) {
			this.hds = rolehsds;
		}

		public double getRolehsfj() {
			return hfj;
		}

		public void setRolehsfj(double rolehsfj) {
			this.hfj = rolehsfj;
		}

		public double getRolexfljl() {
			return exfljl;
		}

		public void setRolexfljl(double rolexfljl) {
			this.exfljl = rolexfljl;
		}

		public double getRolexfljs() {
			return exfljs;
		}

		public void setRolexfljs(double rolexfljs) {
			this.exfljs = rolexfljs;
		}

		public double getRolehsxfkl() {
			return hxfkl;
		}

		public void setRolehsxfkl(double rolehsxfkl) {
			this.hxfkl = rolehsxfkl;
		}

		public double getRolehsxfcd() {
			return hxfcd;
		}

		public void setRolehsxfcd(double rolehsxfcd) {
			this.hxfcd = rolehsxfcd;
		}

		public double getRolehsgh() {
			return hgh;
		}

		public void setRolehsgh(double rolehsgh) {
			this.hgh = rolehsgh;
		}

		public double getRolehsyw() {
			return hyw;
		}

		public void setRolehsyw(double rolehsyw) {
			this.hyw = rolehsyw;
		}

		public double getRolefzml() {
			return ezml;
		}

		public void setRolefzml(double rolefzml) {
			this.ezml = rolefzml;
		}

		public double getJqgjfs() {
			return qgjf;
		}

		public void setJqgjfs(double jqgjfs) {
			this.qgjf = jqgjfs;
		}

		public double getJqfyfs() {
			return qfyf;
		}

		public void setJqfyfs(double jqfyfs) {
			this.qfyf = jqfyfs;
		}

		public double getJqsdfs() {
			return qsdf;
		}

		public void setJqsdfs(double jqsdfs) {
			this.qsdf = jqsdfs;
		}
		public double getRoleklb() {
			return klb;
		}
		public void setRoleklb(double roleklb) {
			this.klb = roleklb;
		}
		public double getQ_qk() {
			return qqk;
		}
		public void setQ_qk(double q_qk) {
			this.qqk = q_qk;
		}
		public double getK_qk() {
			return kqk;
		}
		public void setK_qk(double k_qk) {
			this.kqk = k_qk;
		}
		public double getK_wsxsh() {
			return kwsx;
		}
		public void setK_wsxsh(double k_wsxsh) {
			this.kwsx = k_wsxsh;
		}
		public double getK_zshp() {
			return kzshp;
		}
		public void setK_zshp(double k_zshp) {
			this.kzshp = k_zshp;
		}
		public double getK_zsmp() {
			return kzsmp;
		}
		public void setK_zsmp(double k_zsmp) {
			this.kzsmp = k_zsmp;
		}
		public double getQ_zhssh() {
			return qzhs;
		}
		public void setQ_zhssh(double q_zhssh) {
			this.qzhs = q_zhssh;
		}
		public double getK_jge() {
			return kjge;
		}
		public void setK_jge(double k_jge) {
			this.kjge = k_jge;
		}
		public double getK_qw() {
			return kqw;
		}
		public void setK_qw(double k_qw) {
			this.kqw = k_qw;
		}
		public double getRolehszs() {
			return hzs;
		}
		public void setRolehszs(double rolehszs) {
			this.hzs = rolehszs;
		}
		public double getK_ndhr() {
			return khr;
		}
		public void setK_ndhr(double k_ndhr) {
			this.khr = k_ndhr;
		}
		public double getK_ndqm() {
			return kqm;
		}
		public void setK_ndqm(double k_ndqm) {
			this.kqm = k_ndqm;
		}
		public double getK_ndtm() {
			return ktm;
		}
		public void setK_ndtm(double k_ndtm) {
			this.ktm = k_ndtm;
		}
		public double getK_ndxl() {
			return kxl;
		}
		public void setK_ndxl(double k_ndxl) {
			this.kxl = k_ndxl;
		}
		public double getK_ndfg() {
			return kfg;
		}
		public void setK_ndfg(double k_ndfg) {
			this.kfg = k_ndfg;
		}
		public double getF_f() {
			return f_f;
		}
		public void setF_f(double f_f) {
			this.f_f = f_f;
		}
		public double getF_h() {
			return f_h;
		}
		public void setF_h(double f_h) {
			this.f_h = f_h;
		}
		public double getF_d() {
			return f_d;
		}
		public void setF_d(double f_d) {
			this.f_d = f_d;
		}
		public double getF_xf() {
			return f_xf;
		}
		public void setF_xf(double f_xf) {
			this.f_xf = f_xf;
		}
		public double getF_xh() {
			return f_xh;
		}
		public void setF_xh(double f_xh) {
			this.f_xh = f_xh;
		}
		public double getF_xs() {
			return f_xs;
		}
		public void setF_xs(double f_xs) {
			this.f_xs = f_xs;
		}
		public double getF_xl() {
			return f_xl;
		}
		public void setF_xl(double f_xl) {
			this.f_xl = f_xl;
		}
		public double getF_zs() {
			return f_zs;
		}
		public void setF_zs(double f_zs) {
			this.f_zs = f_zs;
		}
		public double getF_sc() {
			return f_sc;
		}
		public void setF_sc(double f_sc) {
			this.f_sc = f_sc;
		}
		public double getKzml() {
			return kzml;
		}
		public void setKzml(double kzml) {
			this.kzml = kzml;
		}
		public double getKzds() {
			return kzds;
		}
		public void setKzds(double kzds) {
			this.kzds = kzds;
		}
		public double getQzds() {
			return qzds;
		}
		public void setQzds(double qzds) {
			this.qzds = qzds;
		}
		
		public double getKbf() {
			return kbf;
		}
		public void setKbf(double kbf) {
			this.kbf = kbf;
		}
		public double getKbh() {
			return kbh;
		}
		public void setKbh(double kbh) {
			this.kbh = kbh;
		}
		public double getKbs() {
			return kbs;
		}
		public void setKbs(double kbs) {
			this.kbs = kbs;
		}
		public double getKbl() {
			return kbl;
		}
		public void setKbl(double kbl) {
			this.kbl = kbl;
		}
		public double getKbg() {
			return kbg;
		}
		public void setKbg(double kbg) {
			this.kbg = kbg;
		}
		public double getQmh() {
			return qmh;
		}
		public void setQmh(double qmh) {
			this.qmh = qmh;
		}
		public double getQjg() {
			return qjg;
		}
		public void setQjg(double qjg) {
			this.qjg = qjg;
		}
		public double getQqw() {
			return qqw;
		}
		public void setQqw(double qqw) {
			this.qqw = qqw;
		}
		public double getBfy() {
			return bfy;
		}
		public void setBfy(double bfy) {
			this.bfy = bfy;
		}
		public double getBhl() {
			return bhl;
		}
		public void setBhl(double bhl) {
			this.bhl = bhl;
		}
		public double getBhs() {
			return bhs;
		}
		public void setBhs(double bhs) {
			this.bhs = bhs;
		}
		public double getBzd() {
			return bzd;
		}
		public void setBzd(double bzd) {
			this.bzd = bzd;
		}
		public double getBjf() {
			return bjf;
		}
		public void setBjf(double bjf) {
			this.bjf = bjf;
		}
		public double getBjg() {
			return bjg;
		}
		public void setBjg(double bjg) {
			this.bjg = bjg;
		}
		public double getBzs() {
			return bzs;
		}
		public void setBzs(double bzs) {
			this.bzs = bzs;
		}
		public double getByw() {
			return byw;
		}
		public void setByw(double byw) {
			this.byw = byw;
		}
		public double getBmh() {
			return bmh;
		}
		public void setBmh(double bmh) {
			this.bmh = bmh;
		}
		public double getEjs() {
			return ejs;
		}
		public void setEjs(double ejs) {
			this.ejs = ejs;
		}
		public double getExfjs() {
			return exfjs;
		}
		public void setExfjs(double exfjs) {
			this.exfjs = exfjs;
		}
		public double getEwljs() {
			return ewljs;
		}
		public void setEwljs(double ewljs) {
			this.ewljs = ewljs;
		}
		public double getEzsjs() {
			return ezsjs;
		}
		public void setEzsjs(double ezsjs) {
			this.ezsjs = ezsjs;
		}
		public double getEfsds() {
			return efsds;
		}
		public void setEfsds(double efsds) {
			this.efsds = efsds;
		}
		
		public double getQlpl() {
			return qlpl;
		}
		public void setQlpl(double qlpl) {
			this.qlpl = qlpl;
		}
		public double getQlfy() {
			return qlfy;
		}
		public void setQlfy(double qlfy) {
			this.qlfy = qlfy;
		}
		public double getQlcb() {
			return qlcb;
		}
		public void setQlcb(double qlcb) {
			this.qlcb = qlcb;
		}
		public double getQlglv() {
			return qlglv;
		}
		public void setQlglv(double qlglv) {
			this.qlglv = qlglv;
		}
		public double getQlglc() {
			return qlglc;
		}
		public void setQlglc(double qlglc) {
			this.qlglc = qlglc;
		}
		public double getEzs() {
			return ezs;
		}
		public void setEzs(double ezs) {
			this.ezs = ezs;
		}
		
		public double getEwlzs() {
			return ewlzs;
		}
		public void setEwlzs(double ewlzs) {
			this.ewlzs = ewlzs;
		}
		
		public double getBlfcd() {
			return blfcd;
		}
		public void setBlfcd(double blfcd) {
			this.blfcd = blfcd;
		}
		public double getBffcd() {
			return bffcd;
		}
		public void setBffcd(double bffcd) {
			this.bffcd = bffcd;
		}
		public double getBsfcd() {
			return bsfcd;
		}
		public void setBsfcd(double bsfcd) {
			this.bsfcd = bsfcd;
		}
		public double getBhfcd() {
			return bhfcd;
		}
		public void setBhfcd(double bhfcd) {
			this.bhfcd = bhfcd;
		}
		public double getBghcd() {
			return bghcd;
		}
		public void setBghcd(double bghcd) {
			this.bghcd = bghcd;
		}
		public double getBsccd() {
			return bsccd;
		}
		public void setBsccd(double bsccd) {
			this.bsccd = bsccd;
		}
		public double getDzs() {
			return dzs;
		}
		public void setDzs(double dzs) {
			this.dzs = dzs;
		}
		public double getDhf() {
			return dhf;
		}
		public void setDhf(double dhf) {
			this.dhf = dhf;
		}
		public double getDlf() {
			return dlf;
		}
		public void setDlf(double dlf) {
			this.dlf = dlf;
		}
		public double getDff() {
			return dff;
		}
		public void setDff(double dff) {
			this.dff = dff;
		}
		public double getDsf() {
			return dsf;
		}
		public void setDsf(double dsf) {
			this.dsf = dsf;
		}
		public double getDdf() {
			return ddf;
		}
		public void setDdf(double ddf) {
			this.ddf = ddf;
		}
		public double getDfy() {
			return dfy;
		}
		public void setDfy(double dfy) {
			this.dfy = dfy;
		}
		public double getDhl() {
			return dhl;
		}
		public void setDhl(double dhl) {
			this.dhl = dhl;
		}
		public double getDhs() {
			return dhs;
		}
		public void setDhs(double dhs) {
			this.dhs = dhs;
		}
		public double getDgh() {
			return dgh;
		}
		public void setDgh(double dgh) {
			this.dgh = dgh;
		}
		public double getDyw() {
			return dyw;
		}
		public void setDyw(double dyw) {
			this.dyw = dyw;
		}
		public double getDsc() {
			return dsc;
		}
		public void setDsc(double dsc) {
			this.dsc = dsc;
		}
		
		public double getJsf() {
			return jsf;
		}
		public void setJsf(double jsf) {
			this.jsf = jsf;
		}
		public double getJff() {
			return jff;
		}
		public void setJff(double jff) {
			this.jff = jff;
		}
		public double getJlf() {
			return jlf;
		}
		public void setJlf(double jlf) {
			this.jlf = jlf;
		}
		public double getJhf() {
			return jhf;
		}
		public void setJhf(double jhf) {
			this.jhf = jhf;
		}
		public double getJgh() {
			return jgh;
		}
		public void setJgh(double jgh) {
			this.jgh = jgh;
		}
		@Override
		public Ql clone(){
			try {
				return (Ql) super.clone();		
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}
}
