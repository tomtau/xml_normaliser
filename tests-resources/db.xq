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
$nai := function($node as element()) as item()* {()}, $af0 := function($node as element(), $att as attribute()) as attribute()* {
if ((name($node) != 'inproceedings' or name($att) != 'year')) then attribute {name($att)} {$att}
else ()}
, $aa0 := function($node as element()) as attribute()* {
if (name($node) = 'issue') then attribute {'year'} {$node/inproceedings[position() = 1]/@year}

else () }
return
local:transform($nfi, $nai, $af0, $aa0, doc("test.xml")/db)