<?
/*

������ APM ���� ���� �� ���ϸŴ���

��ġ ��� : ������ �ֻ��� ������ ���ε��Ͻ� �� ��й�ȣ�� �ٲ��ֽø� �˴ϴ�.
�� ���� �� ���� ���ô� FTP���ϰ�, ������ ������ ����Ʈ�� ��Ÿ���� �ʽ��ϴ�.
���ο����θ� ����Ͻô°� �����̴ϴ�.

�� �Ժη� �����ؼ� ���������� �����ּ���.
�� ������ ��ó ������ �����ϴ°��� ����մϴ�.
�� �� �ּ��� ���� ������_��;

�̸��� : punchto@hotmail.com
Ȩ������ : http://punchto.dothome.co.kr

*/

$admin_pass = "gu7312!!"; //��й�ȣ �������ּ���.
$max_filesize = 10*1024*1024; //�ѹ��� ���ε� ������ �ִ� ���Ͽ뷮. byte ����. ���� 10MB
$icon_url= "ftp_icon"; //�������� ����ִ� ����
$ftp_file= "ftp.php"; //���� �����ִ� �����̸�.
$textarea_size = "75%"; //�۾��� ���̺��� ���λ�����. �������� �ҽ� ������ ����������?
$textarea_rows = "30"; //�۾��� ���� �ټ�.

// ���⼭���ʹ� �׳� ���νô°� �����ϴ�.
$pwd=chop(realpath("./"));
$root= explode("\\", $pwd );
$root = $root[sizeof($root)-1];
$default_dir = "."; 

if(!$dir){ $dir = $default_dir;} 
// ���� �뷮 ���
$remain_size= diskfreespace("./");
if( $remain_size >= (1024*1024)){$remain_size = number_format(disk_free_space("./")/1024/1024)."MB";}
elseif( $remain_size < (1024*1024)){$remain_size = number_format(disk_free_space("./")/1024)."KB";}
// ������� �뷮���

function check_name($a, $b){      // ����, ���� ������ �̸��� Ư������ �Է� ����
	$nostr=explode(" ","\{ \} \: \; \\\" \' \, \/ \? \! \@ \\$ \# \^ \& \* \; \\\\");
	while($check=each($nostr)){
		if(eregi($check[1], $a)){
			echo"<script>alert (\"".$b."�̸��� $check[1] ���� �� ���� �����ϴ�\"); history.go(-1);</script>";
			exit;
		}
		if(!$a){
			echo"<script>alert(\"������ ".$b."�� �̸��� �Է����ּ���\"); history.go(-1);</script>";
			exit;
			}
	}
}
function check_make($msg){
		global $filename;
		global $content;
		global $dir;
		global $ftp_file;
		echo"<center><br>$dir/$filename <br>$msg<br>";
		echo"<form method=post action=$ftp_file>
		<input type=hidden name=mode value=make>
		<input type=hidden name=filename value=\"$filename\">
		<input type=hidden name=content value=\"$content\">
		<input type=hidden name=dir value=\"$dir\">
		<input type=submit value=�� style=width:60><input type=button value=�ƴϿ� onclick=\"javascript:history.go(-1);\" style=width:60>
		</form>";
}
// ������� �α� ������
if ($action=="login_ok"){ 
	if ($password==$admin_pass){
		SETCOOKIE(logid, crypt($admin_pass));
		echo"<meta http-equiv=refresh content=0;url=$ftp_file>";
		exit;
	}
	else{
		echo"<meta http-equiv=refresh content=0;url=$ftp_file>";
		exit;
	}
}
elseif ($action=="logout"){
	SETCOOKIE(logid, "");
	echo"<meta http-equiv=refresh content=0;url=$ftp_file>";
	exit;
}
elseif ($logid!=crypt($admin_pass, $logid)){	
	echo"<br><br><center><form action=$ftp_file method=post>��й�ȣ <input type=hidden name=action value=login_ok><input style=font-size:12px;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;height:18;margin:1; type=password name=password><input style=font-size:12px;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;height:18;margin:1; type=submit value=�α���><br><br>"; 
	exit;
}
// ������� �α���

//�������丮�� ���ٱ��� �ҽ�
	$realpath=chop(realpath($dir));
	if (!eregi($root, $realpath)){ 
		echo"<script>alert(\"���!! �߸��� �����Դϴ�.\"); history.go(-1);</script>";
		}
	if($pwd == $realpath){
		$dir = $default_dir;
	}
//������� �������丮 ���ٱ��� �ҽ�

//������� �޴�
	echo "<style>
			.name A:link          {color:black; TEXT-decoration: none} 
			.name A:visited       {color:black; TEXT-decoration: none} 
			.name A:active        {color:black; TEXT-decoration: none} 
			.name A:hover         {color:#ff9900; TEXT-decoration: none}

			A:link          {color:blue; TEXT-decoration: none} 
			A:visited       {color:blue; TEXT-decoration: none} 
			A:active        {color:blue; TEXT-decoration: none} 
			A:hover         {color:gray; TEXT-decoration: none}

			submit,input {font-size:8pt;font-family:tahoma;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;margin:1;height=18}
			textarea {font-size:9pt;background-color:white;border-width:1;border-color:rgb(153,153,153); border-style:solid;margin:1;background-image:url('$icon_url/textarea_bg.gif');}

			body,table {font-size:8pt;font-family:tahoma;}

			</style> 
		<br><center><a href=$ftp_file>ó��ȭ��</a> | <a href=\"$ftp_file?action=fmake&dir=$dir\">�������ۼ�</a> | <a href=\"$ftp_file?action=dmake&dir=$dir\">�������ۼ�</a> | <a href=\"$ftp_file?action=upload&dir=$dir\">���Ͼ��ε�</a> | <a href=$ftp_file?action=logout>�α׾ƿ�</a><br><br>";

//������� �޴�

// �� ���� ���� ������
if ($action=="fmake"){
	echo "
		<br><form method=post action=$ftp_file><input type=hidden name=dir value=\"$dir\"><input name=action type=hidden value=fmake_check>\n
		<table border=0 align=center width=$textarea_size><tr><td width=60 align=right>�����̸� : </td><td><input type=text name=filename style=width:100% value=\"$filename\"></td></tr>
		<tr><td width=60 align=right valign=top>�� &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�� : </td><td><textarea name=content style=width:100%; rows=$textarea_rows>";
	if($mode=="modify"){
			$fp = fopen($dir."/".$filename, r);
			while($content =fgets($fp)){
			echo htmlspecialchars($content);
			}
			fclose($fp);
	}
	echo"</textarea></td></tr>
		<tr><td></td><td align=right><input type=submit value=�����><input type=button value=��� onclick=\"javascript:history.go(-1);\"></td></tr></table>
		</form>";
}
//������� �� ���� ���� ������

elseif($action=="fmake_check"){
	$filename=trim($filename);
	check_name($filename, "����");
	$content = htmlspecialchars($content);
	$rdir=opendir($dir);
	while ($list=readdir($rdir)){
		if (strtolower($filename)==strtolower($list)){
			check_make("���� �̸��� ������ �ֽ��ϴ�. ����ðڽ��ϱ�?");
			$exists=1;
		}
	}
	if(!$exists){check_make("������ �����Ͻðڽ��ϱ�?");}
}
elseif($action=="fdelete_check"){
	$filename=trim($filename);
	if(file_exists($dir."/".$filename)){
		unlink($dir."/".$filename);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
	else{
		echo"<center><br>������ �������� �ʽ��ϴ�";
	}
}
elseif($action=="rmdir_check"){
	$rmdir=trim($rmdir);
	if(file_exists($dir."/".$rmdir)){
		$ch=0;
		$rdir=opendir($dir."/".$rmdir);
		while($list = readdir($rdir)) {
			if ($list != "." && $list != ".."){	$ch++;}
		}
		closedir($rdir);
		if ($ch==0){
			rmdir($dir."/".$rmdir);
			echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
			exit;
		}
		else{
			echo"<script>alert(\"���丮�� ����� ������ �� �ֽ��ϴ�\"); history.go(-1);</script>";
		}
	}
	else{
		echo"<center><br>������ �������� �ʽ��ϴ�";
	}
}
else{  // ������� ����Ʈ������
	$filename=trim($filename);
	if($mode=="make"){
			$content =stripslashes(stripslashes($content));
			$fp=fopen($dir."/".trim($filename), w);
			fwrite($fp, $content);
			fclose($fp);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
	if($mode=="delete"){
		unlink($dir."/".$filename);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}

	//������� ���ε� ������
	if ($action=="upload"){
		if ($mode=="upload_ok"){
			$uploadfile = $dir."/".$_FILES[userfile][name];
			if($userfile) { // ������ ���ε� ���� ���
				if($max_filesize<(filesize($_FILES[userfile][tmp_name]))){
					echo("<script>alert (\"$max_filesize �̻� ���ε��� �� �����ϴ�.\"); history.go(-1);</script>");
					exit;
				}
				if(file_exists($uploadfile)) { $exist=1;}  // ������ �̸��� ���� ������ �̹� �����ϴ��� �˻�
				if(!copy($_FILES[userfile][tmp_name], $uploadfile)) {
					echo("<script>alert (\"���Ͼ��ε忡 �����߽��ϴ�\"); history.go(-1);</script>");
					exit;
				}else{
					if($exist){	
						echo("<script>alert (\"���� �������ϴ�\");</script><meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>");
					}else {
					echo("<br><script>alert (\"���� ���� �Ϸ�\");</script><meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>");
					}
				exit;
				}
			}
		}
		else{
		echo"<form enctype=multipart/form-data action=$ftp_file method=post><INPUT TYPE=hidden name=MAX_FILE_SIZE value=$max_filesize><input type=hidden name=dir value=\"$dir\"><input type=hidden name=action value=upload><input type=hidden name=mode value=upload_ok>
		<table align=center cellpadding=0 cellspacing=0 style=\"border-top-width:1; border-top-color:rgb(204,204,204); border-top-style:solid;border-bottom-width:1; border-bottom-color:rgb(204,204,204); border-bottom-style:solid;\">
		<tr height=30><td><input name=userfile type=\"file\" ></td>
		<td></td><td align=right><input type=submit value=���ε� width=60;></td></tr>
		</table></form>";
		}
	} //������� ���ε� ������

// �� ���丮 ���� ������
if ($action=="dmake"){
	if($mode=="dmake_ok"){
			check_name($newdname, "���丮");
			if(file_exists($dir."/".$newdname)){
			echo"<script>alert(\"�����̸��� ���丮�� �ֽ��ϴ�\"); history.go(-1);</script>";
			exit;
			}
		mkdir($dir."/".$newdname, 0777);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
		echo "
		<form method=post action=$ftp_file>
		<input type=hidden name=dir value=\"$dir\">
		<input name=action type=hidden value=dmake>
		<input name=mode type=hidden value=dmake_ok>
		<table align=center cellpadding=0 cellspacing=0 style=\"border-top-width:1; border-top-color:rgb(204,204,204); border-top-style:solid;border-bottom-width:1; border-bottom-color:rgb(204,204,204); border-bottom-style:solid;\">
		<tr height=30><td width=70 align=right>�����̸� : &nbsp;</td><td><input type=text name=newdname style=width:130></td>
		<td></td><td align=right><input type=submit value=�����></td></tr>
		</table>
		</form>";
}
//������� �� ���丮 ���� ������

//������� �̸� ���� ������
if($action=="rename"){
	if ($mode=="rename_ok"){
		if(file_exists($dir."/".$newname) && ($dir."/".$filename)!=($dir."/".$newname) ){
			echo"<script>alert(\"�����̸��� ������ �ֽ��ϴ�\"); history.go(-1);</script>";
			exit;
		}
		rename($dir."/".$filename, $dir."/".$newname);
		echo"<meta http-equiv=refresh content=0;url=$ftp_file?dir=$dir>";
		exit;
	}
	else{
		echo "
		<form method=post action=$ftp_file>
		<input name=action type=hidden value=rename>
		<input type=hidden name=filename value=\"$filename\">
		<input name=mode type=hidden value=rename_ok>
		<input type=hidden name=dir value=\"$dir\">
		<table align=center cellpadding=0 cellspacing=0 style=\"border-top-width:1; border-top-color:rgb(204,204,204); border-top-style:solid;border-bottom-width:1; border-bottom-color:rgb(204,204,204); border-bottom-style:solid;\">
		<tr height=30><td width=70 align=right>�ٲ��̸� : &nbsp;</td><td><input type=text name=newname style=width:130 value=\"$filename\"></td>
		<td></td><td align=right><input type=submit value=�ٲٱ�></td></tr>
		</table>
		</form>";
	}
}
//������� �̸����� ������

//����Ʈ ���
	echo"<table align=center  cellpadding=0 cellspacing=0>
	</td></tr>
	<tr height=22><td colspan=3 ><span style=font-size:8pt; font-family:tahoma;line-height:100%;><font face=tahoma><b>$dir/</a></td><td colspan=4 align=right>���� �뷮 : $remain_size</td></tr>
	<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
	<tr height=22>
		<td width=25 align=center>*</td>
		<td width=190 align=center>�� &nbsp;&nbsp;&nbsp;��</td>
		<td width=50 align=right>ũ ��</td>
		<td width=80 align=center>�ٲﳯ¥</td>
		<td width=20 align=center>E</td>
		<td width=20 align=center>R</td>
		<td width=20 align=center>D</td>
		</tr>
	<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
	";
//������� ����Ʈ���

// ���丮 ���
	$rdir=opendir("$dir");
	while($list_dir=readdir($rdir)){
			if (is_dir($dir."/".$list_dir) && $list_dir!="." && realpath($dir."/".$list_dir)!=($pwd."\\".$icon_url)){
			$filesize = ceil(filesize($dir."/".$list_dir)/1024) . "KB";
			$lasttime =  date("n/d H:i", filemtime($dir."/".$list_dir));
			echo"<tr height=21 bgcolor=f5f5f5>
			<td width=25 align=center><img src=$icon_url/ico_folder.gif></td>
			<td width=190><div class=name>";
			$gouplevel=explode("/", $dir);
			if (($dir."/".$list_dir) == "./.."){echo "";}
			elseif($list_dir == ".."){				 // ".."�� ��� $dir���� ������ ���丮 ��� ����
				$goupdir=".";
				for($cnt=1;$cnt<(sizeof($gouplevel)-1);$cnt++){
					$goupdir = $goupdir."/".$gouplevel[$cnt];
				}
				echo"<a href=\"$ftp_file?dir=$goupdir\">";
			}											//�������
			else{echo"<a href=\"$ftp_file?dir=$dir/$list_dir\">";}
			echo"$list_dir</a></td>
			<td width=50 align=right>��</td>
			<td width=80 align=center><font color=444444>";
			if ($list_dir == ".."){echo "��";}else{echo"$lasttime";}
			echo"
			</td><td width=20 align=center>��</td>
			<td width=20 align=center>";
			if ($list_dir == ".."){echo "��";}else{echo"<a href=\"$ftp_file?action=rename&filename=$list_dir&dir=$dir\"><img src=$icon_url/but_rename.gif border=0 alt=�̸��ٲٱ�></a>";}
			echo"
			</td><td width=20 align=center>";
			if ($list_dir == ".."){echo "��";}else{echo"<a href=\"$ftp_file?action=rmdir_check&rmdir=$list_dir&dir=$dir\"  onclick=\"return confirm('$list_dir ���丮�� �����Ͻðڽ��ϱ�?')\"><img src=$icon_url/but_delete.gif border=0></a>";}
			echo"
			</td></tr>
			<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
				";
			}
	}
//������� ���丮 ���

	//���� ���
	$rdir=opendir("$dir");
	while($list=readdir($rdir)){
			//���� Ȯ���ں� ������
			$filetype = explode(".",  $list);
			$filetype = $filetype[sizeof($filetype)-1];
			switch (strtolower($filetype)){
				case "htm":
					$fileicon="ico_html.gif";
					break;
				case "html":
					$fileicon="ico_html.gif";
					break;
				case "php":
					$fileicon="ico_php.gif";
					break;
				case "gif":
					$fileicon="ico_gif.gif";
					break;
				case "jpg":
					$fileicon="ico_jpg.gif";
					break;
				case "bmp":
					$fileicon="ico_bmp.gif";
					break;
				case "png":
					$fileicon="ico_png.gif";
					break;
				case "js":
					$fileicon="ico_js.gif";
					break;
				case "swf":
					$fileicon="ico_swf.gif";
					break;
				case "fla":
					$fileicon="ico_fla.gif";
					break;
				case "txt":
					$fileicon="ico_txt.gif";
					break;
				case "doc":
					$fileicon="ico_doc.gif";
					break;
				case "mid":
					$fileicon="ico_mid.gif";
					break;
				case "mp3":
					$fileicon="ico_mp3.gif";
					break;
				case "alz":
					$fileicon="ico_alz.gif";
					break;
				case "zip":
					$fileicon="ico_zip.gif";
					break;
				case "rar":
					$fileicon="ico_rar.gif";
					break;
				case "exe":
					$fileicon="ico_exe.gif";
					break;
				case "cgi":
					$fileicon="ico_cgi.gif";
					break;
				case "xls":
					$fileicon="ico_xls.gif";
					break;
				case "ttf":
					$fileicon="ico_ttf.gif";
					break;
				case "css":
					$fileicon="ico_css.gif";
					break;
				default:
					$fileicon="ico_default.gif";
					break;
			}
			//������� �����ܼ���

			if($list!="." && $list!= ".." && realpath($dir."/".$list)!= realpath($pwd."/".$ftp_file) && !is_dir($dir."/".$list)){
			$filesize = number_format(filesize($dir."/".$list)/1024);
			$sum_size= $sum_size+$filesize;
			$sum_obj++;
			$lasttime =  date("n/d H:i", filemtime($dir."/".$list));
			echo"<tr height=21>
			<td width=25 align=center><img src=$icon_url/$fileicon></td>
			<td width=190><div class=name><a href=\"$dir/$list\" target=_blank>$list</a></td>
			<td width=50 align=right><font color=444444>".$filesize."KB</td>
			<td width=80 align=center><font color=444444>$lasttime</td>
			<td width=20 align=center><a href=\"$ftp_file?action=fmake&filename=$list&mode=modify&dir=$dir\"><img src=$icon_url/but_edit.gif border=0 alt=����></a></td>
			<td width=20 align=center><a href=\"$ftp_file?action=rename&filename=$list&dir=$dir\"><img src=$icon_url/but_rename.gif border=0 alt=�̸��ٲٱ�></a>
			</td><td width=20 align=center><a href=\"$ftp_file?action=fdelete_check&filename=$list&dir=$dir\" onclick=\"return confirm('$list ������ �����Ͻðڽ��ϱ�?')\"><img src=$icon_url/but_delete.gif border=0 alt=����></a>
			</td></tr>
			<tr height=1 bgcolor=cccccc><td width=25 colspan=7></td></tr>
			";
		}
	}
	//������� ���ϸ��
	echo"</table>";
	echo "<table width=405 border=0><tr><td align=right><span style=font-size:8pt;color:red;>".number_format($sum_obj)."</span><span style=font-size:7pt;> Files &nbsp;<span style=font-size:8pt;color:red;>".number_format($sum_size)."</span><span style=font-size:7pt;> KB</td></tr></table>";
}
// ������� ���۱�. ��������
echo"<table border=0 height=40><tr><td align=center valign=bottom><span style=font-size:8pt;font-family:tahoma;>Copyright <a href=http://punchto.dothome.co.kr target=_blank>Lepas</a> All Rights Reserved. <br></td></tr></table>";
// ���۱�. ��������
?>