declare function local:transform($nodefilter as function(element()) as xs:boolean, $nodeadd as function(element()) as item()*, $attrfilter as function(element(), attribute()) as attribute()*, $attradd as function(element()) as attribute()*, $input as item()*) as item()* {
for $node in $input
   return 
      typeswitch($node)
        case element()
         return
          if ($nodefilter($node)) then
              element {name($node)} {
                $nodeadd($node),
                $attradd($node),
                for $att in $node/@*
                    return $attrfilter($node, $att)
                ,
                for $child in $node
                   return local:transform($nodefilter, $nodeadd, $attrfilter, $attradd, $child/node())
 
              }
          else ()
        default return $node
};

let $nf1 := function($node as element()) as xs:boolean {xs:boolean('true')},
	$na1 := function($node as element()) as item()* {if (name($node) = 'dep_name') then
                    for $na in distinct-values($node/../constitution/@cno) return element {'newET0'} { 
                    attribute {'cno'} {$na},
                    for $nu in distinct-values($node/../constitution/employee[$node/../constitution/@cno = $na]/@eno) return
                  element {'newET00'} {attribute {'eno'} {$nu}},
                  for $nu in distinct-values($node/../constitution/employee[$node/../constitution/@cno = $na]/@age) return
                 element {'newET01'} {attribute {'age'} {$nu}}
                 } 
                else ()},
  $af1 := function($node as element(), $att as attribute()) as attribute()* {
    if (name($node) != 'constitution' or name($att) != 'cno' ) then
      attribute {name($att)} {$att}
    else ()},
  $aa1 := function($node as element()) as attribute()* { () }
  return
	local:transform($nf1, $na1, $af1, $aa1, doc("test.xml")/company)