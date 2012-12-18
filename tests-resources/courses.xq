declare function local:transform($nodefilter as function(element()) as xs:boolean,$nodeadd as function(element()) as item()*,$attrfilter as function(element(), attribute()) as attribute()*,$attradd as function(element()) as attribute()*, $input as item()*) as item()* {
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

let $aai := function($node as element()) as attribute()* { () },
$afi := function($node as element(), $att as attribute()) as attribute()* {attribute {name($att)} {$att}},
$nfi := function($node as element()) as xs:boolean {xs:boolean('true')},
$nai := function($node as element()) as item()* {()}, $aa0 := function($node as element()) as item()* {
if (name($node) = 'courses') then
for $na in distinct-values($node/course/taken_by/student/name/text()) return element {'newET0'} {element {'name'} {$na}, for $nu in distinct-values($node/course/taken_by/student[name/text() = $na]/@sno) return
 element {'newET00'} {attribute {'sno'} {$nu}}
}
else ()}, $nf0 := function($node as element()) as xs:boolean {name($node) != 'name'}
return
local:transform($nf0, $aa0, $afi, $aai, doc("test.xml")/courses)
