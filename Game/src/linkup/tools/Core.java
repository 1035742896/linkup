package linkup.tools;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import linkup.Chess;

/**
 * ���������Ĳ��֣���ͨ�㷨��ʵ��
 * @author leijing
 *
 */
public class Core {

	//���徲̬����
	private static List<Point> list = new ArrayList<Point>();
	
	//----------�ж��Ƿ���ֱ����ͨ���㷨-------------
	public static boolean canArrived(Chess[][] arr,Point a,Point b){
		//����(���������)a.x == b.x	
		if(a.x == b.x){
			//1��a.y-->b.y
			if(a.y < b.y){
				for(int i = a.y + 1; i < b.y; i++){
					//�м����ϰ���޷���ͨ
					if(arr[a.x][i].getStatus() != 0){
						return false;
					}
				}
				return true;
			}else{
				//2��b.y-->a.y
				for(int i = b.y + 1; i < a.y; i++){
					if(arr[a.x][i].getStatus() != 0){
						return false;
					}
				}
				return true;
			}
		}	
		
		//����(���������)a.y == b.y
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
	
	//------ֱ����ͨ�㷨---------------------------	
	public static List<Point> noCorner(Chess[][] arr,Point a,Point b){
		if(canArrived(arr,a,b)){
			list.add(a);
			list.add(b);
			return list;
		}
		return null;
	}
	
	
	//------һ�ս���ͨ�㷨-------------------------
	public static List<Point> oneCorner(Chess[][] arr,Point a,Point b){
		//�յ�c��a.x == c.x && b.y == c.y
		Point c = new Point(a.x,b.y);
		if(arr[c.x][c.y].getStatus() == 0 && canArrived(arr,a,c) 
				&& canArrived(arr,c,b)){
			list.add(a);
			list.add(c);
			list.add(b);
			return list;
		}
		
		//�յ�d:b.x == c.x && a.y == c.y
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
	
	
	//------���ս���ͨ�㷨-------------------------
	public static List<Point> twoCorner(Chess[][] arr,Point a,Point b){
		//����(�����겻�䣬�������)
		for(int i = 0; i < arr[0].length; i++){
			Point c = new Point(a.x,i);
			if(arr[c.x][c.y].getStatus() == 0 && canArrived(arr,a,c) 
					&& oneCorner(arr,c,b) != null){
				list.add(0,a);
				return list;
			}
		}
	
		//����(�����겻�䣬�������)
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
	
	//-----��ͨ�ܵ��ж��㷨(����Ϊ������ͨ��---------
		public static List<Point> checkLinked(Chess[][] arr,Point a,Point b){
			//�ж��������Ƿ�ͼ��״̬һ�� ��һ��������ͨ
			if(arr[a.x][a.y].getStatus() != arr[b.x][b.y].getStatus()){
				return null;
			}
			list.clear();//ÿ���ж�ǰ����ռ���
			//ֱ����ͨ
			if(noCorner(arr,a,b) != null){
				return list;
			}
			//һ�ս���ͨ
			if(oneCorner(arr,a,b) != null){
				return list;
			}
			//���ս���ͨ
			if(twoCorner(arr,a,b) != null){
				return list;
			}
			return null;
		}
		/**
		 * ϴ��ʵ��:û��������ͼ�� ����ֲ�һ��
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
			//�����ü��ϵ�����,�ŵ�����Ԫ���м���
			Random random = new Random();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					//��ͼ���λ�����·�����ͼ��
					if(arr[i][j].getStatus()!=0){
						int index = random.nextInt(list.size());
						arr[i][j] = list.get(index);
						//�Ƴ� ָ��������Ԫ��
						list.remove(index);
					}
				}
			}		
		}
		
		
		/**
		 * ��ʾ �㷨ʵ��:Ѱ�ҿ�����ͨ��������
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
							//����ͬһ������±�
							if(i==x&&j==y){
								continue;
							}
							Point a = new Point(i,j);
							Point b = new Point(x,y);
							//�ж��Ƿ������ͨ
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
