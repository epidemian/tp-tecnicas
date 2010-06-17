#! /bin/bash
size="-resize 50x50"

convert $size             conveyor_SN.png ../conveyor_SN.png
convert $size -rotate 90  conveyor_SN.png ../conveyor_WE.png
convert $size -rotate 180 conveyor_SN.png ../conveyor_NS.png
convert $size -rotate 270 conveyor_SN.png ../conveyor_EW.png

convert $size             conveyor_SE.png ../conveyor_SE.png
convert $size -rotate 90  conveyor_SE.png ../conveyor_WS.png
convert $size -rotate 180 conveyor_SE.png ../conveyor_NW.png
convert $size -rotate 270 conveyor_SE.png ../conveyor_EN.png

convert $size -flop             conveyor_SE.png ../conveyor_SW.png
convert $size -flop -rotate 90  conveyor_SE.png ../conveyor_WN.png
convert $size -flop -rotate 180 conveyor_SE.png ../conveyor_NE.png
convert $size -flop -rotate 270 conveyor_SE.png ../conveyor_ES.png


convert $size             input_N.png ../input_N.png
convert $size -rotate 90  input_N.png ../input_E.png
convert $size -rotate 180 input_N.png ../input_S.png
convert $size -rotate 270 input_N.png ../input_W.png

convert $size             output_N.png ../output_N.png
convert $size -rotate 90  output_N.png ../output_E.png
convert $size -rotate 180 output_N.png ../output_S.png
convert $size -rotate 270 output_N.png ../output_W.png
