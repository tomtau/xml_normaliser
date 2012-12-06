<db>
{let $root := doc("test.xml")/db
for $co in $root/conf
return <conf>
  <title>{$co/title/text()}</title>
  {for $is in $co/issue
  let $value := $is/inproceedings[position() = 1]/@year
  return <issue year="{$value}">
  {for $in in $is/inproceedings
    return <inproceedings key="{$in/@key}" pages="{$in/@pages}">
    { for $au in $in/author
      return<author> {$au/text()} </author>
      }
      <title>{$in/title/text()}</title>
    </inproceedings>
  }
  </issue>
  }
</conf>
}
</db>