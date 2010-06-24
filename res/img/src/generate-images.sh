#! /bin/bash
size="-resize 50x50"

convert $size             conveyor_SN.png ../conveyor/conveyor_SN.png
convert $size -rotate 90  conveyor_SN.png ../conveyor/conveyor_WE.png
convert $size -rotate 180 conveyor_SN.png ../conveyor/conveyor_NS.png
convert $size -rotate 270 conveyor_SN.png ../conveyor/conveyor_EW.png

convert $size             conveyor_SE.png ../conveyor/conveyor_SE.png
convert $size -rotate 90  conveyor_SE.png ../conveyor/conveyor_WS.png
convert $size -rotate 180 conveyor_SE.png ../conveyor/conveyor_NW.png
convert $size -rotate 270 conveyor_SE.png ../conveyor/conveyor_EN.png

convert $size -flop             conveyor_SE.png ../conveyor/conveyor_SW.png
convert $size -flop -rotate 90  conveyor_SE.png ../conveyor/conveyor_WN.png
convert $size -flop -rotate 180 conveyor_SE.png ../conveyor/conveyor_NE.png
convert $size -flop -rotate 270 conveyor_SE.png ../conveyor/conveyor_ES.png


convert $size             input_N.png ../input/input_N.png
convert $size -rotate 90  input_N.png ../input/input_E.png
convert $size -rotate 180 input_N.png ../input/input_S.png
convert $size -rotate 270 input_N.png ../input/input_W.png

convert $size             output_N.png ../output/output_N.png
convert $size -rotate 90  output_N.png ../output/output_E.png
convert $size -rotate 180 output_N.png ../output/output_S.png
convert $size -rotate 270 output_N.png ../output/output_W.png
