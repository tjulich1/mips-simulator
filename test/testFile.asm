	.text
	addi 	$0, $0, 7
	add	$1, $0, $0
here2:
	beq 	$2, $3, here
	j	end
here:	
	addi 	$2, $2, 1
	j 	here2
end: