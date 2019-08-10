<?php
/*
HOST: ora11g.cs.cityu.edu.hk
PORT: 1522
SERVICE_NAME: orcl.cs.cityu.edu.hk
*/

// This script initializes and visualizes the database 
error_reporting(E_ERROR | E_PARSE);
ini_set('display_errors', '1');

// Create connection to Oracle
$conn = oci_connect("kaiwenxue3", "55199704", "//ora11g.cs.cityu.edu.hk:1522/orcl.cs.cityu.edu.hk");
if (!$conn)
{
    $m = oci_error();
    echo $m['message'], "\n";
    exit;
}
else
{
    print "Connected to Oracle!<br>";
}


// Drop tables
//print "<h2>Dropping Tables HOUSE, CLIENT, AND SEAT..</h2>";
$stid = oci_parse($conn, 'DROP TABLE SEAT');
$r = oci_execute($stid);    // warnings in case tables do not exist is suppressed.
$stid = oci_parse($conn, 'DROP TABLE CLIENT');
$r = oci_execute($stid);
$stid = oci_parse($conn, 'DROP TABLE HOUSE');
$r = oci_execute($stid);

// Creation and Insertion
function parse_execute($cid, $cquery)
{
    $s = oci_parse($cid, $cquery);
    oci_execute($s);
    return $s;
}
$create_query = "CREATE TABLE HOUSE (HID VARCHAR(2) NOT NULL, CONSTRAINT HPK PRIMARY KEY (HID))";
parse_execute($conn, $create_query);
$create_query = "CREATE TABLE CLIENT (EMAIL VARCHAR(30) NOT NULL, NAME VARCHAR(15) NOT NULL, CONSTRAINT CLIENT_PK PRIMARY KEY (EMAIL))";
parse_execute($conn, $create_query);
$create_query = "CREATE TABLE SEAT (SEAT_ID NUMBER(3) NOT NULL, RID VARCHAR(5) NOT NULL, CID NUMBER(3) NOT NULL, HID VARCHAR(5) NOT NULL, CEMAIL VARCHAR(30), CONSTRAINT SEAT_PK PRIMARY KEY (SEAT_ID), CONSTRAINT SEAT_FK FOREIGN KEY (HID) REFERENCES HOUSE (HID), CONSTRAINT SFK FOREIGN KEY (CEMAIL) REFERENCES CLIENT (EMAIL))";
parse_execute($conn, $create_query);
$insert_query = "INSERT INTO HOUSE VALUES ('A')";
parse_execute($conn, $insert_query);
$insert_query = "INSERT INTO HOUSE VALUES ('B')";
parse_execute($conn, $insert_query);
$insert_query = "INSERT INTO HOUSE VALUES ('C')";
parse_execute($conn, $insert_query);
$insert_query = "INSERT INTO HOUSE VALUES ('D')";
parse_execute($conn, $insert_query);
$insert_query = "INSERT INTO HOUSE VALUES ('E')";
parse_execute($conn, $insert_query);

for ($id = 0; $id < 250; $id++)
{
    // attributes to be binded:
    // id = id
    // r = floor id % 50 + criteria ascii
    // c = id % 10
    // house = floor id / 50 ascii
    // email = null
    $r_init = $id % 50;
    if (0 <= $r_init && $r_init < 10) $r = 'A';
    elseif (10 <= $r_init && $r_init < 20) $r = 'B';
    elseif (20 <= $r_init && $r_init < 30) $r = 'C';
    elseif (30 <= $r_init && $r_init < 40) $r = 'D';
    else $r = 'E';
    $c = $id % 10;
    $house = chr(floor($id / 50) + 65);

    $insert_query = "INSERT INTO SEAT VALUES (:idbv, :rbv, :cbv, :housebv, null)";
    $s = oci_parse($conn, $insert_query);
    oci_bind_by_name($s, ":IDBV", $id);
    oci_bind_by_name($s, ":RBV", $r);
    oci_bind_by_name($s, ":CBV", $c);
    oci_bind_by_name($s, ":HOUSEBV", $house);
    oci_execute($s);
}

// select and visualize
function show_content($stid)
{
    while ($row = oci_fetch_array($stid, OCI_ASSOC+OCI_RETURN_NULLS))
    {
        print "<tr>\n";
        foreach ($row as $item)
        {   
            print "    <td>" . ($item !== null ? htmlentities($item, ENT_QUOTES) : "&nbsp;") . "</td>\n";
        }
        print "</tr>\n";
    }
}

$select_query = "SELECT * FROM HOUSE"; 
$stid = parse_execute($conn, $select_query);
print "<table border='1'>\n";
print "<caption>HOUSE</caption>\n";
print "<tr>\n";
print "<th>HID</th>\n";
print "<tr>\n";
show_content($stid);
print "</table>\n";


$select_query = "SELECT * FROM CLIENT";
$stid = parse_execute($conn, $select_query);
print "<table border='1'>\n";
print "<caption>CLIENT</caption>\n";
print "<tr>\n";
print "<th>EMAIL</th><th>NAME</th>\n";
print "<tr>\n";
show_content($stid);
print "</table>\n";

$select_query = "SELECT * FROM SEAT";
$stid = parse_execute($conn, $select_query);
print "<table border='1'>\n";
print "<caption>SEAT</caption>\n";
print "<tr>\n";
print "<th>SEAT_ID</th><th>RID</th><th>CID</th><th>HID</th><th>CEMAIL</th>\n";
print "<tr>\n";
show_content($stid);
print "</table>\n";


// Close the Oracle connection
oci_close($conn);
?>

