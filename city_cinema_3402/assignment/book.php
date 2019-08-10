<!DOCTYPE html>
<html>
<head>
<style>
h1 {
    text-align: center;
}
#splan{
    text-align: center;
}
table{
    padding: 20px; 
    width: 80%;
    height: 150px;
    text-align: center;
    margin-left: auto;
    margin-right: auto;
}
</style>
</head>
<body>

<div id='header'>
<h1>City Cinema -- Book Your Tickets!</h1>
</div>

<?php
$conn = oci_connect("kaiwenxue3", "55199704", "//ora11g.cs.cityu.edu.hk:1522/orcl.cs.cityu.edu.hk");
if (!$conn)
{
   $m = oci_error();
   echo $m['message'], "\n";
   exit;
}

print "<div id='result'>";
$name = $_POST["name"];
$email = $_POST["email"];
$house = $_POST["house"];
$quantity = $_POST["quantity"];
if ($quantity != '')
{
    //update CLIENT
    $s = oci_parse($conn, "SELECT * FROM SEAT WHERE HID='" . $house . "'");
    oci_execute($s);
    $start_num = 0;
    $has_vac = false;
    $start_row = 'A';
    $start_col = 0;
    while ($tuple = oci_fetch_array($s, OCI_ASSOC + OCI_RETURN_NULLS))
    {
        if ($tuple['CEMAIL'] == null && 10 - ($tuple['SEAT_ID'] % 10) >= $quantity)
        {
            $has_vac = true;
            $start_num = $tuple['SEAT_ID'];
            $start_row = $tuple['RID'];
            $start_col = $tuple['CID'];
            break;
        }
    }
    if ($has_vac)
    {
        $s = oci_parse($conn, "INSERT INTO CLIENT VALUES ('" . $email . "', '" . $name . "')");
        oci_execute($s);
        echo "Booking succeeded! Seats for your tickets are: <br>";
        for ($i = 0; $i < $quantity; $i++)
        {
            echo $start_row . strval($start_col + $i) . "<br>";
            $update_query ="UPDATE SEAT SET CEMAIL='" . $email . "' WHERE SEAT_ID=" . strval($start_num + $i);
            $s = oci_parse($conn, $update_query);
            oci_execute($s);
        }
    }
    else
    {
        echo "Booking failed. Sorry, we do not have enough seat for you in this House.<br>";
    }
}
print "</div>";

print "<div id='splan'>";
print "<h2>HOUSE " . $house . " SEATING PLAN</h2>\n";

$stid = oci_parse($conn, "SELECT * FROM SEAT WHERE HID='" . $house . "'"); 
oci_execute($stid);

print "<table border='1'>\n";
for ($i = 0; $i < 5; $i++)
{
    print "<tr>\n";
    print "<td bgcolor=F7F6A6>" . chr($i + 65) . "</td>\n";
    for ($j = 0; $j < 10; $j++)
    {
        $tuple = oci_fetch_array($stid, OCI_ASSOC + OCI_RETURN_NULLS);
        $occupied = ($tuple['CEMAIL'] == null ? 0 : 1);
        print "<td bgcolor=" . ($occupied == 1 ? 'FF4000' : '82FA58') . ">" . $tuple['RID'] . $tuple['CID'] . "</td>\n";
    }
    print "<td bgcolor=F7F6A6>" . chr($i + 65) . "</td>\n";
    print "</tr>\n";
}
print "<tr>";
print "<td></td>";
for ($i = 0; $i < 10; $i++)
{
    print "<td bgcolor=F7F6A6>" . $i . "</td>";
}
print "<td></td>";
print "</tr>";
print "</table>\n";
print "</div>";
?>

<div id='form'>
<form action="" method="post">
<fieldset>
<legend>Booking Information</legend>
Name: <input type="text" name="name" value="wowaka"><br>
Email: <input type="text" name="email" value="wowaka@wowaka.com"><br>
No. of Tickets: <input type="number" name="quantity" min="1" max="10"><br>
HOUSE <select name='house'>
<?php
for ($i = 0; $i < 5; $i++)
{
   $letter = chr($i + 65);
   if ($letter == $house)
   {
       echo "<option value='" . $letter . "' selected>" . $letter . "</option>";
   }
   else
   {
       echo "<option value='" . $letter . "'>" . $letter . "</option>";
   }
}
?>
</select><br>
<input type="submit" value="Submit">
</fieldset>
</form>
<div>

</body>
</html>

