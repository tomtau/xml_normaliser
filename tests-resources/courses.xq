declare function local:transform($nodefilter as function(element()) as xs:boolean, $nodeadd as function(element()) as item()*, $input as item()*) as item()* {
for $node in $input
   return 
      typeswitch($node)
        case element()
         return
          if ($nodefilter($node)) then
              element {name($node)} {
                $nodeadd($node),
                for $att in $node/@*
                      return attribute {name($att)} {$att}
                ,
                for $child in $node
                   return local:transform($nodefilter, $nodeadd, $child/node())
 
              }
          else ()
        default return $node
};

let $f := function($node as element()) as xs:boolean {name($node) != 'name'},
	$g := function($node as element()) as item()* {if (name($node) = 'courses') then
                    for $na in distinct-values($node/course/taken_by/student/name/text()) return element {'newET0'} {  for $nu in distinct-values($node/course/taken_by/student[name/text() = $na]/@sno) return
                  element {'newET00'} {attribute {'sno'} {$nu}},
                  element {'name'} {$na}
                  }             
                else ()}
  return
	local:transform($f, $g, doc("test.xml")/courses)