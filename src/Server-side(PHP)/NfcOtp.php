<!doctype html>
<?php
	// Authorisation details.
	$username = "aniruddhag4@gmail.com";
	$hash = "dbc6317a4f72225b9febc0700183f272e4fde1c3e988ad86e1851c687b5899dd";
	// Config variables. Consult http://api.textlocal.in/docs for more info.
	$test = "0";

	// Data for text message. This is the text message data.
	$sender = "APMART"; // This is who the message appears to be from.
	$numbers = $_POST['number']; // A single number or a comma-seperated list of numbers
	$otp = $_POST['messege'];
	// 612 chars or less
	// A single number or a comma-seperated list of numbers
	$message = rawurlencode($otp.' is Your one time password,
Nfc Pay...');
	$data = "username=".$username."&hash=".$hash."&message=".$message."&sender=".$sender."&numbers=".$numbers."&test=".$test;
	$ch = curl_init('http://api.textlocal.in/send/?');
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	$result = curl_exec($ch); // This is the result from the API
	curl_close($ch);
	echo($result);

?>
<html>
<head>
<meta charset="utf-8">
<title>Untitled Document</title>
</head>

<body>
<form method="post" action="NfcOtp.php">
	<table align="center">
		<tr>
			<td>username:</td>
			<td><input type="text" name="username" placeholder="enter your username"></td>
		</tr>
		<tr>
			<td>hash:</td>
			<td><input type="text" name="hash" placeholder="enter your hash key"></td>
		</tr>
		<tr>
			<td>sender:</td>
			<td><input type="text" name="sender" placeholder="enter your name"></td>
		</tr>
		<tr>
			<td>number:</td>
			<td><input type="text" name="number" placeholder="enter your number"></td>
		</tr>
		<tr>
			<td>message:</td>
			<td><textarea name="messege" placeholder="enter your message"></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="abc" value="send"></td>
		</tr>
	</table>
</form>
</body>
</html>