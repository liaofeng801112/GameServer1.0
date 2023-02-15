package org.come.tool;
import java.lang.reflect.Field;
import java.math.BigDecimal;
/**
 * 遍历实体类成员变量进行赋值工具类
 * @author 叶豪芳
 * @date : 2017年11月29日 下午2:56:10
 */

public class SettModelMemberTool {

	public static void setReflect(Object model, String values, int digit) throws Exception{
		// 获取实体类的所有属性，返回Field数组
		Field[] field = model.getClass().getDeclaredFields();
		// System.out.println("输出获取胡数组长度:"+field.length);
		// System.out.println("输出传送过来胡长度:"+digit);
		if (digit < field.length) {
			// 获取属性的名字
			String name = field[digit].getName();
			Field f = model.getClass().getDeclaredField(name);
			// 关键。。。可访问私有变量
			f.setAccessible(true);
			// 给属性设置
			if (field[digit].getType().toString().equals("class java.lang.Integer")) {
				if (!values.equals("")) {
					f.set(model, Integer.parseInt(values));
				}
			} else if (field[digit].getType().toString().equals("class java.lang.String")) {
				f.set(model, values);
			} else if (field[digit].getType().toString().equals("class java.math.BigDecimal")) {
				if (!values.equals("")) {
					f.set(model, new BigDecimal(values));
				}
			} else if (field[digit].getType().toString().equals("class java.lang.Long")) {
				if (!values.equals("")) {
					f.set(model, Long.parseLong(values));
				}
			} else if (field[digit].getType().toString().equals("class java.lang.Double") || field[digit].getType().toString().equals("double")) {
				if (!values.equals("")) {
					f.set(model, Double.parseDouble(values));
				}
			} else if (field[digit].getType().toString().equals("int")) {
				if (!values.equals("")) {
					f.set(model, Integer.parseInt(values));
				}
			} else if (field[digit].getType().toString().equals("long")) {
				if (!values.equals("")) {
					f.set(model, Long.parseLong(values));
				}
			} 
		}
	}
	
	  /**
     * 数据加载读取HGC
     * 
     * @time 2020年1月7日 下午5:14:17<br>
     * @param model
     * @param values
     * @param digit
     * @throws Exception
     */
    public static void setReflectRelative(Object model, String values, int digit) throws Exception {
    	// 获取实体类的所有属性，返回Field数组
        Field[] field = model.getClass().getDeclaredFields();
        // System.out.println("输出获取胡数组长度:"+field.length);
        // System.out.println("输出传送过来胡长度:"+digit);
        if (digit < field.length) {
            // 获取属性的名字
            String name = field[digit].getName();
            Field f = model.getClass().getDeclaredField(name);
            // 关键。。。可访问私有变量
            f.setAccessible(true);
            // 给属性设置
            if (field[digit].getType().toString().equals("class java.lang.Integer")) {
                if (!values.equals("")) {
                    f.set(model, Integer.parseInt(values));
                }
            } else if (field[digit].getType().toString().equals("class java.lang.String")) {
                f.set(model, values);
            } else if (field[digit].getType().toString().equals("class java.math.BigDecimal")) {
            	if (!values.equals("")) {
					f.set(model, new BigDecimal(values));
				}
            } else if (field[digit].getType().toString().equals("class java.lang.Long")) {
                if (!values.equals("")) {
                    f.set(model, Long.parseLong(values));
                }
            } else if (field[digit].getType().toString().equals("class java.lang.Double")
                    || field[digit].getType().toString().equals("double")) {
                if (!values.equals("")) {
                    f.set(model, Double.parseDouble(values));
                }
            } else if (field[digit].getType().toString().equals("int")) {
                if (!values.equals("")) {
                    f.set(model, Integer.parseInt(values));
                }
            } else if (field[digit].getType().toString().equals("long")) {
                if (!values.equals("")) {
                    f.set(model, Long.parseLong(values));
                }
            }
        }
    }
}
