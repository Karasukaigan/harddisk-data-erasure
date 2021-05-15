/** 
* @author  Karasukaigan 
* @date    2021/5/12 
* @version 1.0.0
*/

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class RunDataErasure {
	public int progressBarValue = 0; //需要循环的次数，依据硬盘剩余空间来定 必要なループ回数、ハードディスクの残りの容量に応じて決定され
	public int fileNum = 1000; //生成的最大文件数 生成されたファイルの数の上限
	//public int fileNum = 50; //测试用 テスト用
	public String[] rs = new String[1024]; //用来储存1MB大小的随机文字列 1MBサイズの文字列を保存するための配列
	public long remainingCapacity = 0; //硬盘剩余空间的变量，单位是MB ハードディスクの残りの容量、単位はMB
	public long margin = 10; //余量，使硬盘不被完全填满 ハードディスクが完全にいっぱいにならないようにするために使用される値
	public String FileExtension = ".karasukaigan"; //自定义文件后缀 ファイル拡張子
	public String path = ""; //硬盘路径 ハードディスク絶対パス
	
	/**
	 * 用来执行擦除操作的方法
	 * 消去を実行するために使用されるメソッド
	 * 
	 * @param hardDiskType
	 * @param originalPath
	 * @param lblNewLabel_6
	 * @param progressBar
	 */
	public void runDE(String hardDiskType, String originalPath, JLabel lblNewLabel_6, JProgressBar progressBar) {
		/*
		 * 该段代码可以保证擦除进度正常更新
		 * このコードは、タスクの進行状況が正常に更新されることを保証できる
		 */
		Dimension d = progressBar.getSize();
		Rectangle rect = new Rectangle(0, 0, d.width, d.height);
		Dimension d1 = lblNewLabel_6.getSize();
		Rectangle rect1 = new Rectangle(0, 0, d1.width, d1.height);
		
		
		path = getPath(originalPath); //获取硬盘路径 ハードディスク絶対パスを取る
		remainingCapacity = getRemainingCapacity(path) - margin; //获取硬盘剩余空间 ハードディスク残りの容量を取る
		//remainingCapacity = 100; //测试用 テスト用
		//System.out.println(remainingCapacity); //测试用 テスト用
		progressBar.setMaximum((int)remainingCapacity); //设置进度条最大值 プログレスバーの最大値を設定
		//System.out.println(path); //测试用 テスト用

		/*
		 * 主循环
		 * メインループ
		 * 判断为SSD还是HDD，执行不同的擦除操作
		 * SSDかHDDか判断し、異なる消去操作を実行
		 */
		if(hardDiskType.equals("SSD")) { //是SSD时 SSDの場合
			progressBarValue = 0;
			int num = 1;
			
			/*
			 * 生成1MB大小的随机文字列，并添加到数组里（一个元素1KB，共1024个元素）
			 * 1MBサイズのランダムな文字列を生成し、それを配列に追加（1要素1KB、合計1024個の要素）
			 */
			for(int i=0; i<1024; i++) { rs[i] = getRandomNumber09azAZSs(); } 
			
			/*
			 * 写入文件，并刷新进度
			 * 1MBサイズのランダムな文字列を生成し、それを配列に追加（1要素1KB、合計1024個の要素）
			 */
			for(int i=1; i<=remainingCapacity; i++) {
				this.createWriteFile(path+"\\"+num+FileExtension,rs);
				
				num++;
				progressBarValue = progressBarValue + 1;
				//System.out.println(progressBarValue); //测试用 テスト用
				lblNewLabel_6.setText("(" + progressBarValue + "/" + remainingCapacity + ")");
				System.out.println("(" + progressBarValue + "/" + remainingCapacity + ")");
				progressBar.setValue(progressBarValue);
				progressBar.paintImmediately(rect);
				lblNewLabel_6.paintImmediately(rect1);
				if(num > fileNum) num = 1;
			}
			//System.out.println("OK"); //测试用 テスト用
			this.deleteFiles(); //删除生成的文件 生成されたファイルを削除
			
		} else if(hardDiskType.equals("HDD")){ //是HDD时 HDDの場合
			for(int times=0; times<3; times++) { //填满HDD3次，以保证数据被完全擦除 データが完全に消去されるために3回実行
				progressBarValue = 0;
				int num = 1;
				
				for(int i=0; i<1024; i++) { rs[i] = getRandomNumber01(); }
				
				for(int i=1; i<=remainingCapacity; i++) {
					this.createWriteFile(path+"\\"+num+FileExtension,rs);
					
					num++;
					progressBarValue = progressBarValue + 1;
					//System.out.println(progressBarValue); //测试用 テスト用
					lblNewLabel_6.setText("(" + progressBarValue + "/" + remainingCapacity + ")");
					System.out.println("(" + progressBarValue + "/" + remainingCapacity + ")");
					progressBar.setValue(progressBarValue);
					progressBar.paintImmediately(rect);
					lblNewLabel_6.paintImmediately(rect1);
					if(num > fileNum) num = 1;
				}
				//System.out.println("OK"); //测试用 テスト用
				this.deleteFiles();
			}
		} else {
			JOptionPane.showMessageDialog(null, "硬盘类型错误！", "错误",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * 获取硬盘路径
	 * ハードディスクパスを取る
	 * @param path
	 * @return
	 */
	public String getPath(String path) { return path.substring(0, 2); }
	
	/**
	 * 获取硬盘剩余空间，单位是MB
	 * ハードディスク残りの容量を取る
	 * 単位はMB
	 * @param path
	 * @return
	 */
	public long getRemainingCapacity(String path) {
		long rc = 0;
		File win = new File(path);
		rc = win.getFreeSpace() / 1024 /1024;
		return rc;
	}
	
	/**
	 * 获取1KB大小的随机文字列，只包含0和1
	 * 0と1のみを含む1KBサイズのランダム文字列を生成
	 * @return
	 */
	public String getRandomNumber01() {
		String r = "";
		for(int i=0; i<1024; i++) r = r + (int)(Math.random()*2);
		return r;
	}
	
	/**
	 * 获取1KB大小的随机文字列，包含0到9
	 * 0から9を含む1KBサイズのランダム文字列を生成
	 * @return
	 */
	public String getRandomNumber09() {
		String r = "";
		for(int i=0; i<1024; i++) r = r + (int)(Math.random()*10);
		return r;
	}
	
	/**
	 * 获取1KB大小的随机文字列，包含0到9和a到z
	 * 0から9、aからzを含む1KBサイズのランダム文字列を生成
	 * @return
	 */
	public String getRandomNumber09az() {
		String r = "";
		String[] a2z = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		for(int i=0; i<1024; i++) {
			if((int)(Math.random()*2) == 0) {
				r = r + (int)(Math.random()*10);
			} else {
				r = r + a2z[(int)(Math.random()*26)];
			}
		}
		return r;
	}
	
	/**
	 * 获取1KB大小的随机文字列，包含0到9、a到z、A到Z
	 * 0から9、aからz、AからZを含む1KBサイズのランダム文字列を生成
	 * @return
	 */
	public String getRandomNumber09azAZ() {
		String r = "";
		String[] a2z = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String[] a2zUL = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		int flag = 0;
		for(int i=0; i<1024; i++) {
			flag = (int)(Math.random()*3);
			if(flag == 0) {
				r = r + (int)(Math.random()*10);
			} else if(flag == 1){
				r = r + a2z[(int)(Math.random()*26)];
			} else {
				r = r + a2zUL[(int)(Math.random()*26)];
			}
		}
		return r;
	}
	
	/**
	 * 获取1KB大小的随机文字列，包含0到9、和a到z、A到Z和一些特殊符号
	 * 0から9、aからz、AからZ、特殊文字を含む1KBサイズのランダム文字列を生成
	 * @return
	 */
	public String getRandomNumber09azAZSs() {
		String r = "";
		String[] a2z = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		String[] a2zUL = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String[] Ss = {"!","@","#","$","%","^","&","*","(",")","-","+","|","~","_","=","\\","`","{","}","[","]",";",":","'","\"","<",">","/","?",",","."};
		int flag = 0;
		for(int i=0; i<1024; i++) {
			flag = (int)(Math.random()*4);
			if(flag == 0) {
				r = r + (int)(Math.random()*10);
			} else if(flag == 1){
				r = r + a2z[(int)(Math.random()*a2z.length)];
			} else if(flag == 2){
				r = r + a2zUL[(int)(Math.random()*a2zUL.length)];
			} else {
				r = r + Ss[(int)(Math.random()*Ss.length)];
			}
		}
		return r;
	}
	
	/**
	 * 生成文件并将数据写入
	 * ファイルを生成し、データを書き込み
	 * @param fileName
	 * @param rs
	 */
	public void createWriteFile(String fileName, String[] rs) {
		File thisFile = new File(fileName);
		BufferedWriter bw;
			try {
				if(!thisFile.exists()) thisFile.createNewFile();
				bw = new BufferedWriter(new FileWriter(fileName,true));
				for(int i=0; i<1024; i++) {
					bw.write(rs[i]);
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 删除生成的文件
	 * 生成されたファイルを削除
	 */
	public void deleteFiles() {
		for(int i=1; i<=fileNum; i++) {
			File file = new File(path+"\\"+i+FileExtension);
			if(file.exists()) file.delete();
			System.out.println("(" + (fileNum-i) + "/" + fileNum + ")");
		}
	}
	
}
