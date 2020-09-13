package linkup.tools;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import linkup.Chess;

/**
 * 连连看核心部分：连通算法的实现
 * @author leijing
 *
 */
public class Core {

	//定义静态集合
	private static List<Point> list = new ArrayList<Point>();
	
	//----------判断是否能直线连通的算法-------------
	public static boolean canArrived(Chess[][] arr,Point a,Point b){
		//横向(横坐标相等)a.x == b.x	
		if(a.x == b.x){
			//1、a.y-->b.y
			if(a.y < b.y){
				for(int i = a.y + 1; i < b.y; i++){
					//中间有障碍物，无法连通
					if(arr[a.x][i].getStatus() != 0){
						return false;
					}
				}
				return true;
			}else{
				//2、b.y-->a.y
				for(int i = b.y + 1; i < a.y; i++){
					if(arr[a.x][i].getStatus() != 0){
						return false;
					}
				}
				return true;
			}
		}	
		
		//纵向(纵坐标相等)a.y == b.y
		if(a.y == b.y){
			for(int i = Math.min(a.x, b.x) + 1; i < Math.max(a.x, b.x); i++){
				if(arr[i][a.y].getStatus() != 0){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	//------直线连通算法---------------------------	
	public static List<Point> noCorner(Chess[][] arr,Point a,Point b){
		if(canArrived(arr,a,b)){
			list.add(a);
			list.add(b);
			return list;
		}
		return null;
	}
	
	
	//------一拐角连通算法-------------------------
	public static List<Point> oneCorner(Chess[][] arr,Point a,Point b){
		//拐点c：a.x == c.x && b.y == c.y
		Point c = new Point(a.x,b.y);
		if(arr[c.x][c.y].getStatus() == 0 && canArrived(arr,a,c) 
				&& canArrived(arr,c,b)){
			list.add(a);
			list.add(c);
			list.add(b);
			return list;
		}
		
		//拐点d:b.x == c.x && a.y == c.y
		Point d = new Point(b.x,a.y);
		if(arr[d.x][d.y].getStatus() == 0 && canArrived(arr,a,d) 
				&& canArrived(arr,d,b)){
			list.add(a);
			list.add(d);
			list.add(b);
			return list;
		}
		return null;
	}
	
	
	//------二拐角连通算法-------------------------
	public static List<Point> twoCorner(Chess[][] arr,Point a,Point b){
		//横向(横坐标不变，纵坐标变)
		for(int i = 0; i < arr[0].length; i++){
			Point c = new Point(a.x,i);
			if(arr[c.x][c.y].getStatus() == 0 && canArrived(arr,a,c) 
					&& oneCorner(arr,c,b) != null){
				list.add(0,a);
				return list;
			}
		}
	
		//纵向(纵坐标不变，横坐标变)
		for(int i = 0; i < arr.length; i++){
			Point d = new Point(i,a.y);
			if(arr[d.x][d.y].getStatus() == 0 && canArrived(arr,a,d) 
					&& oneCorner(arr,d,b) != null){
				list.add(0,a);
				return list;
			}
		}

		return null;
	}
	
	//-----连通总的判断算法(集合为空则不连通）---------
		public static List<Point> checkLinked(Chess[][] arr,Point a,Point b){
			//判断两个点是否图标状态一致 不一致则不能连通
			if(arr[a.x][a.y].getStatus() != arr[b.x][b.y].getStatus()){
				return null;
			}
			list.clear();//每次判断前先清空集合
			//直线连通
			if(noCorner(arr,a,b) != null){
				return list;
			}
			//一拐角连通
			if(oneCorner(arr,a,b) != null){
				return list;
			}
			//二拐角连通
			if(twoCorner(arr,a,b) != null){
				return list;
			}
			return null;
		}
		/**
		 * 洗牌实现:没有消掉的图标 随机分布一次
		 * @param arr
		 */
		public static void refreshArr(Chess[][] arr){
			List<Chess> list = new ArrayList<Chess>();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if(arr[i][j].getStatus()!=0){
						list.add(arr[i][j]);
					}
				}
			}
			//随机获得集合的索引,放到数组元素中即可
			Random random = new Random();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					//有图标的位置重新分配新图标
					if(arr[i][j].getStatus()!=0){
						int index = random.nextInt(list.size());
						arr[i][j] = list.get(index);
						//移除 指定索引的元素
						list.remove(index);
					}
				}
			}		
		}
		
		
		/**
		 * 提示 算法实现:寻找可以连通的两个点
		 * @param arr
		 * @return
		 */
		public static List<Point> remarkArr(Chess[][] arr){
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					if(arr[i][j].getStatus()==0){
						continue;
					}
					for (int x = 0; x < arr.length; x++) {
						for (int y = 0; y < arr[x].length; y++) {
							if(arr[x][y].getStatus()==0){
								continue;
							}	
							//避免同一个点的下标
							if(i==x&&j==y){
								continue;
							}
							Point a = new Point(i,j);
							Point b = new Point(x,y);
							//判断是否可以连通
							if(checkLinked(arr, a, b)!=null){
								List<Point> list = new ArrayList<Point>();
								list.add(a);
								list.add(b);
								return list;
							}		
						}
					}
				}
			}
			return null;
		}
	
}
