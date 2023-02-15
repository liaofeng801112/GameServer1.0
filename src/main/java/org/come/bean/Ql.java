package org.come.bean;

public class Ql {
	//抗物理
		private double rolekwl=0;

		//抗震慑
		private double rolekzs=0;

		//抗风
		private double rolekff=0;

		//抗雷
		private double roleklf=0;

		//坑水
		private double roleksf=0;

		//坑火
		private double rolekhf=0;

		//抗混乱
		private double rolekhl=0;

		//抗昏睡
		private double rolekhs=0;

		//抗封印
		private double rolekfy=0;

		//抗中毒
		private double rolekzd=0;

		//抗遗忘
		private double rolekyw=0;

		//抗鬼火
		private double rolekgh=0;

		//抗三尸
		private double roleksc=0;

		//忽视防御程度
		private double rolehsfyv=0;

		//忽视防御几率
		private double rolehsfyl=0;

		//忽视风法
		private double rolehsff=0;

		//忽视雷法
		private double rolehslf=0;

		//忽视水法
		private double rolehssf=0;

		//忽视火法
		private double rolehshf=0;

		//忽视混乱
		private double rolehshl=0;

		//忽视昏睡
		private double rolehshs=0;

		//忽视封印
		private double rolehsfy=0;

		//忽视中毒
		private double rolehszd=0;

		//强风法
		private double roleqff=0;

		//强雷法
		private double roleqlf=0;

		//强水法
		private double roleqsf=0;

		//强火法
		private double roleqhf=0;

		//强混乱
		private double roleqhl=0;

		//强昏睡
		private double roleqhs=0;
		//强震慑
		private double roleqzs=0;
		//强封印
		private double roleqfy=0;

		//强中毒
		private double roleqzd=0;

		//躲闪率	
		private double rolefdsl=0;

		//反击率
		private double roleffjl=0;

		//反击次数
		private double roleffjv=0;

		//连击率
		private double rolefljl=0;

		//连击次数	
		private double rolefljv=0;

		//命中率
		private double rolefmzl=0;
		//致命率
		private double rolefzml=0;
		//狂暴率
		private double rolefkbl=0;

		//反震率
		private double roleffzl=0;

		//反震程度
		private double roleffzcd=0;

		//中毒率
		private double rolefzdl=0;

		//金
		private double rolewxj=0;

		//木
		private double rolewxm=0;

		//土
		private double rolewxt=0;	

		//水	
		private double rolewxs=0;	

		//火
		private double rolewxh=0;	

		//强力克金
		private double rolewxqkj=0;

		//强力克木
		private double rolewxqkm=0;

		//强力克土
		private double rolewxqkt=0;

		//强力克水
		private double rolewxqks=0;

		//强力克火
		private double rolewxqkh=0;

		//无属性伤害
		private double rolewsxsh=0;

		//风法伤害
		private double roleffsh=0;

		//雷法伤害	
		private double rolelfsh=0;

		//水法伤害
		private double rolesfsh=0;

		//火法伤害
		private double rolehfsh=0;
		
		//雷法狂暴
		private double rolelfkb=0;
		
		//风法狂暴
		private double roleffkb=0;
		
		//水法狂暴
		private double rolesfkb=0;
		
		//火法狂暴
		private double rolehfkb=0;
		
		//毒伤害
		private double rolezdsh=0;

		//鬼火伤害
		private double roleghsh=0;

		//三尸伤害
		private double rolesssh=0;
		
	    //强鬼火
		private double rolegstronghostfire=0;
		
		//强遗忘
		private double rolestrongforget=0;
		
		//强三尸血
		private double rolestrongbodyblood=0;
		
		//强三尸血回血程度
		private double rolestrongbodyblooddeep=0;

		  //鬼火狂暴
	    private double roleghkb=0;

	    //三尸虫狂暴
	    private double rolesskb=0;
	    
	  //忽视躲闪
	    private double rolehsds=0;
	  //忽视反击
	    private double rolehsfj=0;
	  //仙法连击率
	    private double rolexfljl=0;
	  //仙法连击次数
	    private double rolexfljs=0;
	  //忽视仙法抗性率
	    private double rolehsxfkl=0;
	  //忽视仙法抗性程度
	    private double rolehsxfcd=0;
		//忽视鬼火
		private double rolehsgh=0;
		//加强攻击法术效果
		private double jqgjfs=0;
		//加强防御法术效果
		private double jqfyfs=0;
		//加强速度法术效果
		private double jqsdfs=0;
		
		
		//忽视遗忘
		private double rolehsyw=0;
		public void Reset(){
			rolekwl=0;rolekzs=0;rolekff=0;roleklf=0;roleksf=0;rolekhf=0;
            rolekhl=0;rolekhs=0;rolekfy=0;rolekzd=0;rolekyw=0;rolekgh=0;
            roleksc=0;rolehsfyv=0;rolehsfyl=0;rolehsff=0;rolehslf=0;
            rolehssf=0;rolehshf=0;rolehshl=0;rolehshs=0;rolehsfy=0;
            rolehszd=0;roleqff=0;roleqlf=0;roleqsf=0;roleqhf=0;
            roleqhl=0;roleqhs=0;roleqzs=0;roleqfy=0;roleqzd=0;
            rolefdsl=0;roleffjl=0;roleffjv=0;rolefljl=0;rolefljv=0;
            rolefmzl=0;rolefkbl=0;roleffzl=0;roleffzcd=0;rolefzdl=0;
            rolewxj=0;rolewxm=0;rolewxt=0;rolewxs=0;rolewxh=0;
            rolewxqkj=0;rolewxqkm=0;rolewxqkt=0;rolewxqks=0;
            rolewxqkh=0;rolewsxsh=0;roleffsh=0;rolelfsh=0;rolesfsh=0;
            rolehfsh=0;rolelfkb=0;roleffkb=0;rolesfkb=0;rolehfkb=0;
			rolezdsh=0;roleghsh=0;rolesssh=0;rolegstronghostfire=0;
			rolestrongforget=0;rolestrongbodyblood=0;rolestrongbodyblooddeep=0;
			roleghkb=0;rolesskb=0;rolehsds=0;rolehsfj=0;rolexfljl=0;rolexfljs=0;
			rolehsxfkl=0;rolehsxfcd=0;rolehsgh=0;rolehsyw=0;rolefzml=0;
			jqgjfs=0;jqfyfs=0;jqsdfs=0;
		}
		
		public double getRolekwl() {
			return rolekwl;
		}

		public void setRolekwl(double rolekwl) {
			this.rolekwl = rolekwl;
		}

		public double getRolekzs() {
			return rolekzs;
		}

		public void setRolekzs(double rolekzs) {
			this.rolekzs = rolekzs;
		}

		public double getRolekff() {
			return rolekff;
		}

		public void setRolekff(double rolekff) {
			this.rolekff = rolekff;
		}

		public double getRoleklf() {
			return roleklf;
		}

		public void setRoleklf(double roleklf) {
			this.roleklf = roleklf;
		}

		public double getRoleksf() {
			return roleksf;
		}

		public void setRoleksf(double roleksf) {
			this.roleksf = roleksf;
		}

		public double getRolekhf() {
			return rolekhf;
		}

		public void setRolekhf(double rolekhf) {
			this.rolekhf = rolekhf;
		}

		public double getRolekhl() {
			return rolekhl;
		}

		public void setRolekhl(double rolekhl) {
			this.rolekhl = rolekhl;
		}

		public double getRolekhs() {
			return rolekhs;
		}

		public void setRolekhs(double rolekhs) {
			this.rolekhs = rolekhs;
		}

		public double getRolekfy() {
			return rolekfy;
		}

		public void setRolekfy(double rolekfy) {
			this.rolekfy = rolekfy;
		}

		public double getRolekzd() {
			return rolekzd;
		}

		public void setRolekzd(double rolekzd) {
			this.rolekzd = rolekzd;
		}

		public double getRolekyw() {
			return rolekyw;
		}

		public void setRolekyw(double rolekyw) {
			this.rolekyw = rolekyw;
		}

		public double getRolekgh() {
			return rolekgh;
		}

		public void setRolekgh(double rolekgh) {
			this.rolekgh = rolekgh;
		}

		public double getRoleksc() {
			return roleksc;
		}

		public void setRoleksc(double roleksc) {
			this.roleksc = roleksc;
		}

		public double getRolehsfyv() {
			return rolehsfyv;
		}

		public void setRolehsfyv(double rolehsfyv) {
			this.rolehsfyv = rolehsfyv;
		}

		public double getRolehsfyl() {
			return rolehsfyl;
		}

		public void setRolehsfyl(double rolehsfyl) {
			this.rolehsfyl = rolehsfyl;
		}

		public double getRolehsff() {
			return rolehsff;
		}

		public void setRolehsff(double rolehsff) {
			this.rolehsff = rolehsff;
		}

		public double getRolehslf() {
			return rolehslf;
		}

		public void setRolehslf(double rolehslf) {
			this.rolehslf = rolehslf;
		}

		public double getRolehssf() {
			return rolehssf;
		}

		public void setRolehssf(double rolehssf) {
			this.rolehssf = rolehssf;
		}

		public double getRolehshf() {
			return rolehshf;
		}

		public void setRolehshf(double rolehshf) {
			this.rolehshf = rolehshf;
		}

		public double getRolehshl() {
			return rolehshl;
		}

		public void setRolehshl(double rolehshl) {
			this.rolehshl = rolehshl;
		}

		public double getRolehshs() {
			return rolehshs;
		}

		public void setRolehshs(double rolehshs) {
			this.rolehshs = rolehshs;
		}

		public double getRolehsfy() {
			return rolehsfy;
		}

		public void setRolehsfy(double rolehsfy) {
			this.rolehsfy = rolehsfy;
		}

		public double getRolehszd() {
			return rolehszd;
		}

		public void setRolehszd(double rolehszd) {
			this.rolehszd = rolehszd;
		}

		public double getRoleqff() {
			return roleqff;
		}

		public void setRoleqff(double roleqff) {
			this.roleqff = roleqff;
		}

		public double getRoleqlf() {
			return roleqlf;
		}

		public void setRoleqlf(double roleqlf) {
			this.roleqlf = roleqlf;
		}

		public double getRoleqsf() {
			return roleqsf;
		}

		public void setRoleqsf(double roleqsf) {
			this.roleqsf = roleqsf;
		}

		public double getRoleqhf() {
			return roleqhf;
		}

		public void setRoleqhf(double roleqhf) {
			this.roleqhf = roleqhf;
		}

		public double getRoleqhl() {
			return roleqhl;
		}

		public void setRoleqhl(double roleqhl) {
			this.roleqhl = roleqhl;
		}

		public double getRoleqhs() {
			return roleqhs;
		}

		public void setRoleqhs(double roleqhs) {
			this.roleqhs = roleqhs;
		}

		public double getRoleqzs() {
			return roleqzs;
		}

		public void setRoleqzs(double roleqzs) {
			this.roleqzs = roleqzs;
		}

		public double getRoleqfy() {
			return roleqfy;
		}

		public void setRoleqfy(double roleqfy) {
			this.roleqfy = roleqfy;
		}

		public double getRoleqzd() {
			return roleqzd;
		}

		public void setRoleqzd(double roleqzd) {
			this.roleqzd = roleqzd;
		}

		public double getRolefdsl() {
			return rolefdsl;
		}

		public void setRolefdsl(double rolefdsl) {
			this.rolefdsl = rolefdsl;
		}

		public double getRoleffjl() {
			return roleffjl;
		}

		public void setRoleffjl(double roleffjl) {
			this.roleffjl = roleffjl;
		}

		public double getRoleffjv() {
			return roleffjv;
		}

		public void setRoleffjv(double roleffjv) {
			this.roleffjv = roleffjv;
		}

		public double getRolefljl() {
			return rolefljl;
		}

		public void setRolefljl(double rolefljl) {
			this.rolefljl = rolefljl;
		}

		public double getRolefljv() {
			return rolefljv;
		}

		public void setRolefljv(double rolefljv) {
			this.rolefljv = rolefljv;
		}

		public double getRolefmzl() {
			return rolefmzl;
		}

		public void setRolefmzl(double rolefmzl) {
			this.rolefmzl = rolefmzl;
		}

		public double getRolefkbl() {
			return rolefkbl;
		}

		public void setRolefkbl(double rolefkbl) {
			this.rolefkbl = rolefkbl;
		}

		public double getRoleffzl() {
			return roleffzl;
		}

		public void setRoleffzl(double roleffzl) {
			this.roleffzl = roleffzl;
		}

		public double getRoleffzcd() {
			return roleffzcd;
		}

		public void setRoleffzcd(double roleffzcd) {
			this.roleffzcd = roleffzcd;
		}

		public double getRolefzdl() {
			return rolefzdl;
		}

		public void setRolefzdl(double rolefzdl) {
			this.rolefzdl = rolefzdl;
		}

		public double getRolewxj() {
			return rolewxj;
		}

		public void setRolewxj(double rolewxj) {
			this.rolewxj = rolewxj;
		}

		public double getRolewxm() {
			return rolewxm;
		}

		public void setRolewxm(double rolewxm) {
			this.rolewxm = rolewxm;
		}

		public double getRolewxt() {
			return rolewxt;
		}

		public void setRolewxt(double rolewxt) {
			this.rolewxt = rolewxt;
		}

		public double getRolewxs() {
			return rolewxs;
		}

		public void setRolewxs(double rolewxs) {
			this.rolewxs = rolewxs;
		}

		public double getRolewxh() {
			return rolewxh;
		}

		public void setRolewxh(double rolewxh) {
			this.rolewxh = rolewxh;
		}

		public double getRolewxqkj() {
			return rolewxqkj;
		}

		public void setRolewxqkj(double rolewxqkj) {
			this.rolewxqkj = rolewxqkj;
		}

		public double getRolewxqkm() {
			return rolewxqkm;
		}

		public void setRolewxqkm(double rolewxqkm) {
			this.rolewxqkm = rolewxqkm;
		}

		public double getRolewxqkt() {
			return rolewxqkt;
		}

		public void setRolewxqkt(double rolewxqkt) {
			this.rolewxqkt = rolewxqkt;
		}

		public double getRolewxqks() {
			return rolewxqks;
		}

		public void setRolewxqks(double rolewxqks) {
			this.rolewxqks = rolewxqks;
		}

		public double getRolewxqkh() {
			return rolewxqkh;
		}

		public void setRolewxqkh(double rolewxqkh) {
			this.rolewxqkh = rolewxqkh;
		}

		public double getRolewsxsh() {
			return rolewsxsh;
		}

		public void setRolewsxsh(double rolewsxsh) {
			this.rolewsxsh = rolewsxsh;
		}

		public double getRoleffsh() {
			return roleffsh;
		}

		public void setRoleffsh(double roleffsh) {
			this.roleffsh = roleffsh;
		}

		public double getRolelfsh() {
			return rolelfsh;
		}

		public void setRolelfsh(double rolelfsh) {
			this.rolelfsh = rolelfsh;
		}

		public double getRolesfsh() {
			return rolesfsh;
		}

		public void setRolesfsh(double rolesfsh) {
			this.rolesfsh = rolesfsh;
		}

		public double getRolehfsh() {
			return rolehfsh;
		}

		public void setRolehfsh(double rolehfsh) {
			this.rolehfsh = rolehfsh;
		}

		public double getRolelfkb() {
			return rolelfkb;
		}

		public void setRolelfkb(double rolelfkb) {
			this.rolelfkb = rolelfkb;
		}

		public double getRoleffkb() {
			return roleffkb;
		}

		public void setRoleffkb(double roleffkb) {
			this.roleffkb = roleffkb;
		}

		public double getRolesfkb() {
			return rolesfkb;
		}

		public void setRolesfkb(double rolesfkb) {
			this.rolesfkb = rolesfkb;
		}

		public double getRolehfkb() {
			return rolehfkb;
		}

		public void setRolehfkb(double rolehfkb) {
			this.rolehfkb = rolehfkb;
		}

		public double getRolezdsh() {
			return rolezdsh;
		}

		public void setRolezdsh(double rolezdsh) {
			this.rolezdsh = rolezdsh;
		}

		public double getRoleghsh() {
			return roleghsh;
		}

		public void setRoleghsh(double roleghsh) {
			this.roleghsh = roleghsh;
		}

		public double getRolesssh() {
			return rolesssh;
		}

		public void setRolesssh(double rolesssh) {
			this.rolesssh = rolesssh;
		}

		public double getRolegstronghostfire() {
			return rolegstronghostfire;
		}

		public void setRolegstronghostfire(double rolegstronghostfire) {
			this.rolegstronghostfire = rolegstronghostfire;
		}

		public double getRolestrongforget() {
			return rolestrongforget;
		}

		public void setRolestrongforget(double rolestrongforget) {
			this.rolestrongforget = rolestrongforget;
		}

		public double getRolestrongbodyblood() {
			return rolestrongbodyblood;
		}

		public void setRolestrongbodyblood(double rolestrongbodyblood) {
			this.rolestrongbodyblood = rolestrongbodyblood;
		}

		public double getRolestrongbodyblooddeep() {
			return rolestrongbodyblooddeep;
		}

		public void setRolestrongbodyblooddeep(double rolestrongbodyblooddeep) {
			this.rolestrongbodyblooddeep = rolestrongbodyblooddeep;
		}

		public double getRoleghkb() {
			return roleghkb;
		}

		public void setRoleghkb(double roleghkb) {
			this.roleghkb = roleghkb;
		}

		public double getRolesskb() {
			return rolesskb;
		}

		public void setRolesskb(double rolesskb) {
			this.rolesskb = rolesskb;
		}

		public double getRolehsds() {
			return rolehsds;
		}

		public void setRolehsds(double rolehsds) {
			this.rolehsds = rolehsds;
		}

		public double getRolehsfj() {
			return rolehsfj;
		}

		public void setRolehsfj(double rolehsfj) {
			this.rolehsfj = rolehsfj;
		}

		public double getRolexfljl() {
			return rolexfljl;
		}

		public void setRolexfljl(double rolexfljl) {
			this.rolexfljl = rolexfljl;
		}

		public double getRolexfljs() {
			return rolexfljs;
		}

		public void setRolexfljs(double rolexfljs) {
			this.rolexfljs = rolexfljs;
		}

		public double getRolehsxfkl() {
			return rolehsxfkl;
		}

		public void setRolehsxfkl(double rolehsxfkl) {
			this.rolehsxfkl = rolehsxfkl;
		}

		public double getRolehsxfcd() {
			return rolehsxfcd;
		}

		public void setRolehsxfcd(double rolehsxfcd) {
			this.rolehsxfcd = rolehsxfcd;
		}

		public double getRolehsgh() {
			return rolehsgh;
		}

		public void setRolehsgh(double rolehsgh) {
			this.rolehsgh = rolehsgh;
		}

		public double getRolehsyw() {
			return rolehsyw;
		}

		public void setRolehsyw(double rolehsyw) {
			this.rolehsyw = rolehsyw;
		}

		public double getRolefzml() {
			return rolefzml;
		}

		public void setRolefzml(double rolefzml) {
			this.rolefzml = rolefzml;
		}

		public double getJqgjfs() {
			return jqgjfs;
		}

		public void setJqgjfs(double jqgjfs) {
			this.jqgjfs = jqgjfs;
		}

		public double getJqfyfs() {
			return jqfyfs;
		}

		public void setJqfyfs(double jqfyfs) {
			this.jqfyfs = jqfyfs;
		}

		public double getJqsdfs() {
			return jqsdfs;
		}

		public void setJqsdfs(double jqsdfs) {
			this.jqsdfs = jqsdfs;
		}
		
}
