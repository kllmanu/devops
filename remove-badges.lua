function Link(el)
  -- Check if the link contains an Image element
  if #el.content == 1 and el.content[1].t == "Image" then
    local img = el.content[1]
    -- Check if either the link target or the image source contains badge/workflow URLs
    if string.match(img.src, "badge.svg") then
      return {} -- Returning an empty table removes the element entirely
    end
  end
end
