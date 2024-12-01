import {readFileToGridMap} from "../helpers/helpers.ts";
import * as path from "jsr:@std/path";

console.log(
    readFileToGridMap(
        path.join("..", "inputs", "day1-1.txt")
    )
);

Deno.exit(0)