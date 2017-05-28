export class Part {
    constructor(
        public id?: number,
        public partName?: string,
        public partLengthDimension?: number,
        public partHeightWidthDiameter?: number,
        public ratioLD?: number,
        public noOfHoles?: number,
        public mass?: number,
        public partCode?: string,
    ) {
    }
}
