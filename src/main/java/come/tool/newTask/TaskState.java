package come.tool.newTask;
/**
 * 任务状态
 * @author Administrator
 *
 */
public class TaskState {
	/**-2 超时取消任务*/
	public static final int quxiaoTime   = 9;
	/**-1 取消任务*/
	public static final int quxiao       = 8;
	/**0 不可接状态*/
	public static final int cannotAccept = 0;
	/**1 可接  但还未接的状态*/
	public static final int canAccept    = 1;
	/**2 已接  正在进行中*/
	public static final int doTasking    = 2;
	/**3 完成  未领奖*/
	public static final int completeTask = 3;
	/**4 完成  已领奖*/
	public static final int finishTask   = 4;	
}