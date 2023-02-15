package org.come.thread;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.entity.Mount;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

public class MountManage implements DataBaseManage {
    private List<Mount> addList = new ArrayList<>();
    private List<BigDecimal> delList = new ArrayList<>();
    private List<Mount> updList = new ArrayList<>();

    @Override
    public void add(Object object) {
        addList.add((Mount) object);
        if (addList.size() > DataBaseManage.a) {
            try {
                AllServiceUtil.getMountService().insertMountList(addList);
            } catch (Exception e) {
                for (int i = addList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getMountService().insertMountSingle(addList.get(i));
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
                AllServiceUtil.getMountService().deleteMountsByMidList(delList);
            } catch (Exception e) {
                for (int i = delList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getMountService().deleteMountsByMidsql(delList.get(i));
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
        updList.add((Mount) object);
        if (updList.size() > DataBaseManage.a) {
            try {
                AllServiceUtil.getMountService().updateMountList(updList);
            } catch (Exception e) {
                for (int i = updList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getMountService().updateMountsql(updList.get(i));
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
        if (delList.size() != 0) {
            try {
                AllServiceUtil.getMountService().deleteMountsByMidList(delList);
            } catch (Exception e) {
                for (int i = delList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getMountService().deleteMountsByMidsql(delList.get(i));
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
                AllServiceUtil.getMountService().updateMountList(updList);
            } catch (Exception e) {
                for (int i = updList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getMountService().updateMountsql(updList.get(i));
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

        if (addList.size() != 0) {
            try {
                AllServiceUtil.getMountService().insertMountList(addList);
            } catch (Exception e) {
                for (int i = addList.size() - 1; i >= 0; i--) {
                    try {
                        AllServiceUtil.getMountService().insertMountSingle(addList.get(i));
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
}
