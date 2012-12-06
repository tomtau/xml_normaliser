declare function local:transform($input as item()*) as item()* {
for $node in $input
   return 
      typeswitch($node)
        case element()
           return
              element {name($node)} {
                if (name($node) = 'issue') then attribute {'year'} {$node/inproceedings[position() = 1]/@year}
                else (),

                for $att in $node/@*
                   return
                      if (name($node) != 'inproceedings' or name($att) != 'year') then
                        attribute {name($att)} {$att}
                      else ()
                   
                ,
                for $child in $node
                   return local:transform($child/node())
 
              }
        default return $node
};

local:transform(doc("test.xml")/db)