import * as path from "jsr:@std/path";

export type Coordinate = {
  row: number;
  column: number;
  character: string;
};

export function readFile(file: string): string {
  const decoder = new TextDecoder("utf-8");
  const fileContent = Deno.readFileSync(
    file,
  );
  return decoder.decode(fileContent).replaceAll("\r\n", "\n");
}
export function readFileLines(file: string) {
  const fileContent = readFile(file);
  return fileContent.split("\n");
}
export function readFileToGrid(file: string): Coordinate[] {
  const fileContent = readFileLines(file);
  const coordinates: Coordinate[] = [];
  for (const [rowIdx, row] of Object.entries(fileContent)) {
    for (const [columnIdx, character] of Object.entries(row)) {
      coordinates.push({
        row: +rowIdx,
        column: +columnIdx,
        character,
      })
    }
  }

  return coordinates;
}

export function readFileToGridMap(file: string): Map<string, string> {
  const map = new Map<string, string>();
  for (const coordinate of readFileToGrid(file)) {
    map.set(`${coordinate.row}-${coordinate.column}`, coordinate.character)
  }
  return map;
}
