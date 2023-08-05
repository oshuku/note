package demo.atzhuwang.java;

public class Demo {
	public static void main(String[] args) {
		// 将一个8位以内的整数，拆成数组
		// 即如何获取每一位上的数字
		
		// 假设一个整数
		int num = 123456;
		
		// 准备一个数组
		int[] arr = new int[8];
		
		// 利用循环去每个位上的数字
		int index = 0;
		while (num > 0) {
			arr[index] = num % 10;
			index++;  // 数组实际的长度
			num /= 10;
		}
		
		// 取反
		for(int i = 0; i < index / 2; i++) {
			int temp = 0;
			temp = arr[i];
			arr[i] = arr[index - 1 - i];
			arr[index - 1 - i] = temp;
		}
		
		// 输出
		for (int i = 0; i < index; i++) {
			System.out.print(arr[i]);
		}
	}	
}

