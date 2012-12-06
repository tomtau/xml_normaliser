declare function local:transform($attradd as function(element()) as attribute()*, $attrfilter as function(element(), attribute()) as attribute()*, $input as item()*) as item()* {
for $node in $input
   return 
      typeswitch($node)
        case element()
           return
              element {name($node)} {
                $attradd($node),

                for $att in $node/@*
                   return
                      $attrfilter($node,$att)
                   
                ,
                for $child in $node
                   return local:transform($attradd, $attrfilter, $child/node())
 
              }
        default return $node
};

let $f := function($node as element()) as attribute()* {
			if (name($node) = 'issue') then attribute {'year'} {$node/inproceedings[position() = 1]/@year}
            else ()
            },
    $g := function($node as element(), $att as attribute()) as attribute()* {
            if (name($node) != 'inproceedings' or name($att) != 'year') then
            	attribute {name($att)} {$att}
            else ()
            }
return
  local:transform($f, $g, doc("test.xml")/db)