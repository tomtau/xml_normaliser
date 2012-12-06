declare function local:transform($input as item()*) as item()* {
for $node in $input
   return 
      typeswitch($node)
        case element()
         return
          if (name($node) != 'name') then
              element {name($node)} {
                if (name($node) = 'courses') then
                    for $na in distinct-values($node/course/taken_by/student/name/text()) return element {'newET0'} {  for $nu in distinct-values($node/course/taken_by/student[name/text() = $na]/@sno) return
                  element {'newET00'} {attribute {'sno'} {$nu}},
                  element {'name'} {$na}
                  }             
                else (),
                for $att in $node/@*
                      return attribute {name($att)} {$att}
                ,
                for $child in $node
                   return local:transform($child/node())
 
              }
          else ()
        default return $node
};

local:transform(doc("test.xml")/courses)