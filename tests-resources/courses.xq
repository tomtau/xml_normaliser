<courses>
{let $root := doc("test.xml")/courses
 for $co in $root/course
  return <course cno="{ $co/@cno }"> 
<title>{$co/title/text()}</title>
<taken_by>
{ for $student in $co/taken_by
return <student sno="{$student/student/@sno }">
<grade>{$student/student/grade/text()}</grade>
</student>
}
</taken_by>
</course>
}
{
let $root := doc("test.xml")/courses
for $na in distinct-values($root/course/taken_by/student/name/text())
return <newET0>
{ for $nu in distinct-values($root/course/taken_by/student[name/text() =
$na]/@sno)
return <newET00 sno="{ $nu }" />,
<name> { $na } </name>
}
</newET0>  
}
</courses>