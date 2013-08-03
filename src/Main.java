package length;
import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.FileReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.RandomAccessFile; 
import java.io.Reader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Stack;

public class Main{
  static double rs[]=new double[10];
	static int j=0;
	
	public void readFileExe(String fileName){ 
		File file = new File(fileName); 
		BufferedReader reader = null; 
		try {
			reader = new BufferedReader(new FileReader(file)); 
			String tempString = null;
			Vector<Length> v=new Vector<Length>();
			while ((tempString = reader.readLine()) != null){
			if(tempString.isEmpty())
			{
				continue;
			}
			else if(tempString.contains("="))
			{
				String[] result = tempString.split("\\s");
				Length l=new Length(result[1],Double.parseDouble(result[3]));
				v.add(l);
			}
			else if(!tempString.contains("+") && !tempString.contains("-"))
			{
				String[] result = tempString.split("\\s");
				if(result[1].contains("feet"))
				{
				    rs[j]=Double.parseDouble(result[0])*0.03048;
				    j++;
				}
				else{
					for(int i=0;i<v.size();i++)
					{
						if(result[1].contains(v.get(i).name))
						{
							rs[j]=Double.parseDouble(result[0])*v.get(i).num;
							j++;
							break;
						}
					}	
				}
				
			}
			else{
				
				Stack<Double> opnd=new Stack<Double>();  //操作数栈
				Stack<Character> optr=new Stack<Character>();
				String[] result = tempString.split("\\+ |\\- ");
				for(int i=result.length-1;i>=0;i--)
				{
					String[] result1=result[i].split("\\s");
					double rs = 0;
					if(result1[1].contains("feet"))
					{
					    rs=Double.parseDouble(result1[0])*0.03048;
					}
					else
					{
						for(int j=0;j<v.size();j++)
						{
							if(result1[1].contains(v.get(j).name))
							{
								rs=Double.parseDouble(result1[0])*v.get(j).num;
								break;
							}
						}
					}
					opnd.push(rs);
				}
				char[] judgeresult=tempString.toCharArray();
				double res=0,right = 0,left = 0;
				for(int k=judgeresult.length-1;k>=0;k--)
				{
					if(judgeresult[k]=='+' || judgeresult[k]=='-')
					{
						optr.push(judgeresult[k]);
					}
				}
				while(!opnd.isEmpty())
				{
					left=opnd.pop();
					if(optr.isEmpty())
					{
						rs[j]=left;
						j++;
						break;
					}
					right=opnd.pop();
					switch(optr.pop())
					{
					 case '+':
					     res=left+right;
					     opnd.push(res);
					     break;
					 case '-':
						 res=left-right;
						 opnd.push(res);
						 break;
					}
				}
				
			}	
		} 
			reader.close(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally { 
			if (reader != null){ 
		try { 
			reader.close(); 
		} catch (IOException e1) { 
		 } 
	   }	 
	 } 
	}
	
	public static void main(String args[]){
		Main lg=new Main();
		String path=System.getProperty("user.dir")+"/input.txt";
		lg.readFileExe(path);
		DecimalFormat df = new DecimalFormat("0.00");
		String str;
		str="448142893@qq.com\r\n\r\n";
		for(int i=0;i<10;i++)
		{
			str+=df.format(rs[i]).toString()+" m\r\n";
		}
		try{
		String path1=System.getProperty("user.dir")+"/output.txt";
		File f=new File(path1);
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(str.getBytes());
		fos.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
