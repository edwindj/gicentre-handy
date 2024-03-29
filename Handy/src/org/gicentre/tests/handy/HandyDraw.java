package org.gicentre.tests.handy;

import org.gicentre.handy.HandyRenderer;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;

//  ****************************************************************************************
/** Wrapper around a HandyRenderer that allows Handy drawing to be turned on or off inside
 *  a sketch without having to change any of the sketch's drawing commands. To use, put the
 *  drawing code between calls to <code>startHandy()</code> and <code>stopHandy()</code> in
 *  a sketch.
 *  @author Aidan Slingsby and Jo Wood, giCentre, City University London.
 *  @version 1.1, 11th April, 2012.
 */ 
//  ****************************************************************************************

/* This file is part of Handy sketchy drawing library. Handy is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Handy is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this
 * source code (see COPYING.LESSER included with this source code). If not, see 
 * http://www.gnu.org/licenses/.
 */

public class HandyDraw extends PGraphicsJava2D{

	// -------------------------------- Object Variables ---------------------------------  
	
	private HandyRenderer handyRenderer;
	private PGraphics prevG; //The sketch's g when startHandy() is called 
	
	private boolean useSuper=false;
	
	// ----------------------------------- Constructor -----------------------------------
	
	public HandyDraw(PApplet sketch){
		this.parent=sketch;
		this.handyRenderer=new HandyRenderer(sketch);
		this.handyRenderer.setGraphics(this);	
		this.setSize(sketch.width, sketch.height);
	}
	
	// ------------------------------------- Methods ------------------------------------- 
	
	/** Turns on handy rendering. This should be paired with a call to <code>stopHandy()</code>
	 *  once sketchy rendering is complete.
	 */
	public void startHandy(){
		prevG=this.parent.g;
		if (this.prevG instanceof PGraphicsJava2D==false)
			System.err.println("Please only use the JAVA2D renderer.");
		this.parent.g=this;
		beginDraw();
		this.style(prevG.getStyle());			// Set the style of this to that of the sketch
		this.setMatrix(prevG.getMatrix());
		if (prevG.smooth) {
			this.smooth();
		}
		else{
			this.noSmooth();
		}
		if (parent.width!=this.width || parent.height!=this.height) {
			this.setSize(parent.width, parent.height);
		}

		background(255,0);  						// Erase previous drawing to screen buffer and set to transparent
	}
	

	/** Turns off handy rendering. This should be paired with a call to <code>startHandy()</code>
	 *  before any drawing that needs to be in a sketchy style.
	 */
	public void stopHandy(){
		endDraw();
		this.prevG.style(this.getStyle());	// Set the style of the sketch to that of this
		this.prevG.setMatrix(this.getMatrix());
		
		this.prevG.pushMatrix();
		this.prevG.resetMatrix();
		this.prevG.pushStyle();
		this.prevG.noTint(); 					// Temporarily remove tint while we draw handy screen buffer.
		this.prevG.image(this.get(),0,0);
		this.prevG.popStyle();
		this.prevG.popMatrix();

		this.parent.g=prevG;
	}
	
	// ----------------------- Overridden Processing Draw Methods ------------------------- 
	
	/** Draws a 2D point at the given location. 
	 *  @param x x coordinate of the point.
	 *  @param y y coordinate of the point.
	 */
	@Override
	public void point(float x, float y){
		if (useSuper){
			super.point(x, y);
		}
		else{
			useSuper=true;
			handyRenderer.point(x, y);
			useSuper=false;
		}
	}
	
	/** Draws a 3D point at the given location. 
	 *  @param x x coordinate of the point.
	 *  @param y y coordinate of the point.
	 *  @param z z coordinate of the point.
	 */
	@Override
	public void point(float x, float y, float z){
		if (useSuper){
			super.point(x, y, z);
		}
		else{
			useSuper=true;
			handyRenderer.point(x, y, z);
			useSuper=false;
		}
	}
	
	/** Draws a 2D line between the given coordinate pairs. 
	 *  @param x1 x coordinate of the start of the line.
	 *  @param y1 y coordinate of the start of the line.
	 *  @param x2 x coordinate of the end of the line.
	 *  @param y2 y coordinate of the end of the line.
	 */
	@Override
	public void line(float x1, float y1, float x2, float y2){
		if (useSuper){
			super.line(x1, y1, x2, y2);
		}
		else{
			useSuper=true;
			handyRenderer.line(x1, y1, x2, y2);
			useSuper=false;
		}
	}
	
	/** Draws a 3D line between the given coordinate pairs. 
	 *  @param x1 x coordinate of the start of the line.
	 *  @param y1 y coordinate of the start of the line.
	 *  @param z1 z coordinate of the start of the line.
	 *  @param x2 x coordinate of the end of the line.
	 *  @param y2 y coordinate of the end of the line.
	 *  @param z2 z coordinate of the end of the line.
	 */
	@Override
	public void line(float x1, float y1, float z1, float x2, float y2, float z2){
		if (useSuper){
			super.line(x1, y1, z1, x2, y2, z2);
		}
		else{
			useSuper=true;
			handyRenderer.line(x1, y1, z1, x2, y2, z2);
			useSuper=false;
		}
	}
	
	/** Draws an ellipse using the given location and dimensions. By default the x,y coordinates
	 *  will be centre of the ellipse, but the meanings of these parameters can be changed with
	 *  Processing's <code>ellipseMode()</code> command.
	 *  @param x x coordinate of the ellipse's position
	 *  @param y y coordinate of the ellipse's position.
	 *  @param w Width of the ellipse (but see modifications possible with ellipseMode())
	 *  @param h Height of the ellipse (but see modifications possible with ellipseMode())
	 */
	@Override
	public void ellipse(float x, float y,float w, float h){
		if (useSuper){
			super.ellipse(x,y,w,h);
		}
		else{
			useSuper=true;
			handyRenderer.ellipse(x, y, w, h);
			useSuper=false;
		}
	}
	
	/** Draws an arc along the outer edge of an ellipse defined by the x,y, w and h parameters.
	 *  This version allows the maximum random offset of the arc to be set explicitly.
	 *  @param x x coordinate of the ellipse's position around which this arc is defined.
	 *  @param y y coordinate of the ellipse's position around which this arc is defined
	 *  @param w Width of the ellipse around which this arc is defined (but see modifications possible with ellipseMode())
	 *  @param h Height of the ellipse around which this arc is defined (but see modifications possible with ellipseMode())
	 *  @param start Angle to start the arc in radians.
	 *  @param stop Angle to stop the arc in radians.
	 */
	@Override
	public void arc(float x, float y,float w, float h, float start, float stop){
		if (useSuper){
			super.arc(x,y,w,h,start,stop);
		}
		else{
			useSuper=true;
			handyRenderer.arc(x, y, w, h, start,stop);
			useSuper=false;
		}
	}

	
	/** Draws a quadrilateral shape. Similar to a rectangle but angles not constrained to 90 degrees.
	 *  Coordinates can proceed in either a clockwise or anti-clockwise direction.
	 *  @param x1 x coordinate of the first quadrilateral vertex.
	 *  @param y1 y coordinate of the first quadrilateral vertex.
	 *  @param x2 x coordinate of the second quadrilateral vertex.
	 *  @param y2 y coordinate of the second quadrilateral vertex.
	 *  @param x3 x coordinate of the third quadrilateral vertex.
	 *  @param y3 y coordinate of the third quadrilateral vertex.
	 *  @param x4 x coordinate of the fourth quadrilateral vertex.
	 *  @param y4 y coordinate of the fourth quadrilateral vertex.
	 */
	@Override
	public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		if (useSuper){
			super.quad(x1, y1, x2, y2, x3, y3, x4, y4);
		}
		else{
			useSuper=true;
			handyRenderer.quad(x1, y1, x2, y2, x3, y3, x4, y4);
			useSuper=false;
		}
	}
	
	
	/** Draws a rectangle using the given location and dimensions. By default the x,y coordinates
	 *  will be the top left of the rectangle, but the meanings of these parameters can be 
	 *  changed with Processing's <code>rectMode()</code> command.
	 *  @param x x coordinate of the rectangle position
	 *  @param y y coordinate of the rectangle position.
	 *  @param w Width of the rectangle (but see modifications possible with rectMode())
	 *  @param h Height of the rectangle (but see modifications possible with rectMode())
	 */
	@Override
	public void rect(float x, float y,float w, float h){
		if (useSuper){
			super.rect(x,y,w,h);
		}
		else{
			useSuper=true;
			handyRenderer.rect(x, y, w, h);
			useSuper=false;
		}
	}

	/** Draws a triangle through the three pairs of coordinates.
	 *  @param x1 x coordinate of the first triangle vertex.
	 *  @param y1 y coordinate of the first triangle vertex.
	 *  @param x2 x coordinate of the second triangle vertex.
	 *  @param y2 y coordinate of the second triangle vertex.
	 *  @param x3 x coordinate of the third triangle vertex.
	 *  @param y3 y coordinate of the third triangle vertex.
	 */
	@Override
	public void triangle(float x1, float y1,float x2, float y2,float x3, float y3){
		if (useSuper){
			super.triangle(x1,y1,x2,y2,x3,y3);
		}
		else{
			useSuper=true;
			handyRenderer.triangle(x1,y1,x2,y2,x3,y3);
			useSuper=false;
		}
	}
	
	
	/** Draws 3D cube with the given unit dimension.
	 *  @param bSize Size of each dimension of the cube.
	 */
	@Override
	public void box(float bSize) {
		if (useSuper){
			super.box(bSize);
		}
		else{
			useSuper=true;
			handyRenderer.box(bSize);
			useSuper=false;
		}
	}
	
	/** Draws 3D box with the given dimensions.
	 *  @param bWidth Width of the box.
	 *  @param bHeight Height of the box.
	 *  @param bDepth Depth of the box.
	 */
	@Override
	public void box(float bWidth, float bHeight, float bDepth) {
		if (useSuper){
			super.box(bWidth, bHeight, bDepth);
		}
		else{
			useSuper=true;
			handyRenderer.box(bWidth, bHeight, bDepth);
			useSuper=false;
		}
	}

	/** Starts a new shape of type <code>POLYGON</code>. This must be paired with a call to 
	 * <code>endShape()</code> or one of its variants.
	 */
	@Override	
	public void beginShape(){
		this.beginShape(POLYGON);
	}

	/** Starts a new shape of the type specified in the mode parameter. This must be paired
	 * with a call to <code>endShape()</code> or one of its variants.
	 *	@param mode either POINTS, LINES, TRIANGLES, TRIANGLE_FAN, TRIANGLE_STRIP, QUADS, QUAD_STRIP	 
	 */
	@Override	
	public void beginShape(int mode){
		if (useSuper){
			super.beginShape(mode);
		}
		else{
			useSuper=true;
			handyRenderer.beginShape(mode);
			useSuper=false;
		}
	}

	
	@Override
	/** Adds a 2d vertex to a shape that was started with a call to <code>beginShape()</code> 
	 *  or one of its variants.
	 *  @param x x coordinate of vertex to add.
	 *  @param y y coordinate of vertex to add.
	 */
	public void vertex(float x, float y){
		if (useSuper){
			super.vertex(x,y);
		}
		else{
			useSuper=true;
			handyRenderer.vertex(x,y);
			useSuper=false;
		}
	}
	
	@Override
	/** Adds a 3d vertex to a shape that was started with a call to <code>beginShape()</code> 
	 *  or one of its variants.
	 *  @param x x coordinate of vertex to add.
	 *  @param y y coordinate of vertex to add.
	 *  @param z z coordinate of vertex to add.
	 */
	public void vertex(float x, float y, float z){
		if (useSuper){
			super.vertex(x,y,z);
		}
		else{
			useSuper=true;
			handyRenderer.vertex(x,y,z);
			useSuper=false;
		}
	}
	
	@Override
	/** Adds a 2d vertex to a shape or line that has curved edges. That shape should have been
	 *  started with a call to <code>beginShape()</code> without any parameter.
	 *  @param x x coordinate of vertex to add.
	 *  @param y y coordinate of vertex to add.
	 */
	public void curveVertex(float x, float y){
		if (useSuper){
			super.curveVertex(x,y);
		}
		else{
			useSuper=true;
			handyRenderer.curveVertex(x,y);
			useSuper=false;
		}
	}
	
	@Override
	/** Adds a 3d vertex to a shape or line that has curved edges. That shape should have been
	 *  started with a call to <code>beginShape()</code> without any parameter.
	 *  @param x x coordinate of vertex to add.
	 *  @param y y coordinate of vertex to add.
	 *  @param z z coordinate of vertex to add.
	 */
	public void curveVertex(float x, float y,float z){
		if (useSuper){
			super.curveVertex(x,y,z);
		}
		else{
			useSuper=true;
			handyRenderer.curveVertex(x,y,z);
			useSuper=false;
		}
	}
	
	/** Ends a shape definition. This should have been paired with a call to 
	 *  <code>beginShape()</code> or one of its variants. Note that this version
	 *  will not close the shape if the last vertex does not match the first one.
	 */
	@Override
	public void endShape(){
		if (useSuper){
			super.endShape();
		}
		else{
			useSuper=true;
			handyRenderer.endShape();
			useSuper=false;
		}
	}
		
	
	/** Ends a shape definition. This should have been paired with a call to 
	 *  <code>beginShape()</code> or one of its variants. If the mode parameter
	 *  is <code>CLOSE</code> the shape will be closed.
	 */
	@Override
	public void endShape(int mode) 
	{
		if (useSuper){
			super.endShape(mode);
		}
		else{
			useSuper=true;
			handyRenderer.endShape(mode);
			useSuper=false;
		}
	}
}
