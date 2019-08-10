<?php
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
// select and visualize

function parse_execute($cid, $cquery)
{
    $s = oci_parse($cid, $cquery);
    oci_execute($s);
    return $s;
}

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
