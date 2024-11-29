package main

import (
	"fmt"
	"github.com/samber/lo"
	"github.com/teunw/advent-of-code-24/helpers"
)

func main() {
	content := helpers.ReadToGridOrFatal("1", "1")

	lines := lo.Map(*content, func(coord helpers.GridCoordinate, index int) string {
		return fmt.Sprintf("Coordinate %d-%d: %s",
			coord.Row,
			coord.Column,
			string(coord.Content))
	})
	for _, i := range lines {
		fmt.Println(i)
	}
	fmt.Println(len(*content))
}
