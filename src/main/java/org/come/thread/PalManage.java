package org.come.thread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.entity.Pal;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class PalManage implements DataBaseManage {
    private List<Pal> addList = new ArrayList<>();
    private List<BigDecimal> delList = new ArrayList<>();
    private List<Pal> updList = new ArrayList<>();

    @Override
    public void add(Object object) {
        addList.add((Pal) object);
        if (addList.size() > DataBaseManage.a) {
            try {
                AllServiceUtil.getPalService().insertPalList(addList);
            } catch (Exception e) {
                for (int i = addList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getPalService().insertPalSql(addList.get(i));
                    } catch (Exception e2) {
                        try {
                            System.out.println("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(addList.get(i)));
                            WriteOut.addtxt("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(addList.get(i)), 999);
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            addList.clear();
        }
    }

    @Override
    public void del(Object object) {
        delList.add((BigDecimal) object);
        if (delList.size() > DataBaseManage.a) {
            try {
                AllServiceUtil.getPalService().deletePalList(delList);
            } catch (Exception e) {
                for (int i = delList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getPalService().deletePalSql(delList.get(i));
                    } catch (Exception e2) {
                        try {
                            System.out.println("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(delList.get(i)));
                            WriteOut.addtxt("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(delList.get(i)), 999);
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            delList.clear();
        }

    }

    @Override
    public void upd(Object object) {
        updList.add((Pal) object);
        if (updList.size() > DataBaseManage.a) {
            try {
                AllServiceUtil.getPalService().updatePalList(updList);
            } catch (Exception e) {
                for (int i = updList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getPalService().updatePalSql(updList.get(i));
                    } catch (Exception e2) {
                        try {
                            System.out.println("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(updList.get(i)));
                            WriteOut.addtxt("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(updList.get(i)), 999);
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            updList.clear();
        }
    }

    @Override
    public void ClearList() {
        if (addList.size() != 0) {
            try {
                AllServiceUtil.getPalService().insertPalList(addList);
            } catch (Exception e) {
                for (int i = addList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getPalService().insertPalSql(addList.get(i));
                    } catch (Exception e2) {
                        try {
                            System.out.println("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(addList.get(i)));
                            WriteOut.addtxt("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(addList.get(i)), 999);
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            addList.clear();
        }

        if (delList.size() != 0) {
            try {
                AllServiceUtil.getPalService().deletePalList(delList);
            } catch (Exception e) {
                for (int i = delList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getPalService().deletePalSql(delList.get(i));
                    } catch (Exception e2) {
                        try {
                            System.out.println("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(delList.get(i)));
                            WriteOut.addtxt("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(delList.get(i)), 999);
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            delList.clear();
        }

        if (updList.size() != 0) {
            try {
                AllServiceUtil.getPalService().updatePalList(updList);
            } catch (Exception e) {
                for (int i = updList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getPalService().updatePalSql(updList.get(i));
                    } catch (Exception e2) {
                        try {
                            System.out.println("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(updList.get(i)));
                            WriteOut.addtxt("同步数据库物品属性:" + GsonUtil.getGsonUtil().getgson().toJson(updList.get(i)), 999);
                        } catch (Exception e3) {
                        }
                    }
                }
            }
            updList.clear();
        }
    }

}
