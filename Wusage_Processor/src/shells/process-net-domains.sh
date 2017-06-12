#!/bin/bash

sed \
	-e '/.comcast.net/ i\
ISP  \\' \
	<$1 > res-$1
sed '/\\$/N; s/\\\n//; ' res-$1 >alter-$1




