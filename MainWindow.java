/** 
* 硬盘数据擦除工具
* ハードディスクデータ消去ツール
* 
* @author  Karasukaigan 
* @date    2021/5/12 
* @version 1.0.0
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JProgressBar;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private String absolutePath = System.getProperty("user.dir"); //程序所在绝对路径 プログラムの絶対パス
	private int ran = (int)(Math.random() * (999999 - 100000) + 100000); //生成验证码 認証コードを生成

	/**
	 * 启动程序
	 * プログラム起動
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setLocationRelativeTo(null); //将窗口放在屏幕中央 ウィンドウを画面の中央に置く
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 主窗口
	 * メインウィンドウ
	 * 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public MainWindow() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		setResizable(false);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //改变GUI样式 GUIのスタイルを変更
		setTitle("硬盘擦除工具");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序 ウィンドウを閉じたらプログラムを終了
		setBounds(100, 100, 248, 210); //设置窗口大小 ウィンドウサイズを設定
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][]"));
		
		/*
		 * 显示程序所在目录，这也是将被执行擦除操作的目录
		 * 消去操作が実行されるディレクトリのパスを表示
		 */
		JLabel lblNewLabel = new JLabel("当前位置：");
		panel.add(lblNewLabel, "cell 0 0,alignx right");
		JLabel lblNewLabel_1 = new JLabel(absolutePath); 
		panel.add(lblNewLabel_1, "cell 1 0,alignx left");
		
		/*
		 * 选择硬盘类型，HDD或者SSD，针对不同的硬盘类型所执行的擦除方法不同
		 * ハードディスクの種類（HDDまたはSSD）を選択し、ハードディスクの種類によって異なる消去方法を実行
		 */
		JLabel lblNewLabel_2 = new JLabel("硬盘类型：");
		panel.add(lblNewLabel_2, "cell 0 1,alignx right");
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setMaximumRowCount(10);
		comboBox.addItem("HDD");
		comboBox.addItem("SSD");
		panel.add(comboBox, "cell 1 1,alignx left");
		
		/*
		 * 输入随机的验证码，此步骤是为了防止误操作
		 * 誤操作を防ぐため、認証コードを入力する必要がある
		 */
		JLabel lblNewLabel_3 = new JLabel("验证码：");
		panel.add(lblNewLabel_3, "cell 0 2,alignx right");
		textField = new JTextField(8);
		textField.setDocument(new JTextFieldLimit(6)); //限制文本框可输入字符数为6 入力桁数を6桁に制限
		panel.add(textField, "cell 1 2,alignx left");
		JLabel lblNewLabel_4 = new JLabel(ran + "");
		panel.add(lblNewLabel_4, "cell 1 3");
		
		JLabel lblNewLabel_7 = new JLabel("进度：");
		panel.add(lblNewLabel_7, "cell 0 5,alignx right");
		
		JLabel lblNewLabel_6 = new JLabel("(0/0)");
		panel.add(lblNewLabel_6, "cell 1 5,growx");
		
		JProgressBar progressBar = new JProgressBar();
		panel.add(progressBar, "cell 1 6,alignx left");
		
		/*
		 * 开始擦除按钮
		 * 開始ボタン
		 */
		JButton btnNewButton = new JButton("开始擦除");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //鼠标点击事件 マウスクリックイベント
				String vc = ran + "";
				//判断验证码正确性 認証コードが正しいかどうかを判断
				if(vc.equals(textField.getText())) { 
					RunDataErasure rde = new RunDataErasure(); //生成用来执行擦除命令的对象 消去コマンドを実行するオブジェクトを生成
					rde.runDE((String)comboBox.getSelectedItem(),absolutePath,lblNewLabel_6,progressBar); //执行擦除命令 消去コマンドを実行
				} else {
					JOptionPane.showMessageDialog(null, "验证码错误，请重新输入！", "错误",JOptionPane.WARNING_MESSAGE); //验证码错误提示 認証コードエラープロンプト
					textField.setText("");
					ran = (int)(Math.random() * (999999 - 100000) + 100000); //重置验证码 認証コードリセット
					lblNewLabel_4.setText(ran + "");
				}
			}
		});
		panel.add(btnNewButton, "cell 1 4");	
		
	}
	
}
