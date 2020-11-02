package io.github.NadhifRadityo.Objects.Utilizations;

import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;
import com.sun.javafx.geom.Vec4f;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.BVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMat2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMat3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FMatNxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.FVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.GenType;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMat2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMat3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IMatNxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.IVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMat2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMat3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LMatNxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.MatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.MatNxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMat2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMat3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMatMxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SMatNxN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.SVecN;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Size;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.VecN;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.Pool.PoolCleaner;
import io.github.NadhifRadityo.Objects.Utilizations.Easing.Easing;
import sun.misc.Unsafe;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

public class GenTypeUtils extends NumberUtils {

	/*
	 * Notes!
	 *
	 * Source: https://thebookofshaders.com/06/
	 *
	 * Defining color using an x, y and z notation can be confusing and misleading, right?
	 * That's why there are other ways to access this same information, but with different names.
	 * The values of .x(), .y() and .z() can also be called .r, .g and .b, and .s, .t and .p.
	 * (.s, .t and .p are usually used for spatial coordinates of a texture, which we'll see in a later chapter.)
	 * You can also access the data in a vector by using the index position, [0], [1] and [2].
	 *
	 * The following lines show all the ways to access the same data:
	 * -| vec4 vector;
	 * -| vector[0] = vector.r = vector.x() = vector.s;
	 * -| vector[1] = vector.g = vector.y() = vector.t;
	 * -| vector[2] = vector.b = vector.z() = vector.p;
	 * -| vector[3] = vector.a = vector.w() = vector.q;
	 *
	 */

	// Random
	public static final int RAND_MAX = 32767;
	public static Vec2 randVec2(double min, double max, Random random) { Vec2 result = new Vec2(); rand(min, max, random, result.d); return result; }
	public static Vec3 randVec3(double min, double max, Random random) { Vec3 result = new Vec3(); rand(min, max, random, result.d); return result; }
	public static Vec4 randVec4(double min, double max, Random random) { Vec4 result = new Vec4(); rand(min, max, random, result.d); return result; }
	public static VecN randVecN(int n, double min, double max, Random random) { VecN result = new VecN(n); rand(min, max, random, result.d); return result; }
	public static Vec2 randX(Vec2 x, double min, double max, Random random) { rand(min, max, random, x.d); return x; }
	public static Vec3 randX(Vec3 x, double min, double max, Random random) { rand(min, max, random, x.d); return x; }
	public static Vec4 randX(Vec4 x, double min, double max, Random random) { rand(min, max, random, x.d); return x; }
	public static VecN randX(VecN x, double min, double max, Random random) { rand(min, max, random, x.d); return x; }

	public static Vec2 randVec2(double min, double max) { return randVec2(min, max, PublicRandom.getRandom()); }
	public static Vec3 randVec3(double min, double max) { return randVec3(min, max, PublicRandom.getRandom()); }
	public static Vec4 randVec4(double min, double max) { return randVec4(min, max, PublicRandom.getRandom()); }
	public static VecN randVecN(int n, double min, double max) { return randVecN(n, min, max, PublicRandom.getRandom()); }
	public static Vec2 randX(Vec2 x, double min, double max) { randX(x, min, max, PublicRandom.getRandom()); return x; }
	public static Vec3 randX(Vec3 x, double min, double max) { randX(x, min, max, PublicRandom.getRandom()); return x; }
	public static Vec4 randX(Vec4 x, double min, double max) { randX(x, min, max, PublicRandom.getRandom()); return x; }
	public static VecN randX(VecN x, double min, double max) { randX(x, min, max, PublicRandom.getRandom()); return x; }

	public static double rand() { return rand(0, RAND_MAX, PublicRandom.getRandom()); }
	public static Vec2 randVec2() { return randVec2(0, RAND_MAX); }
	public static Vec3 randVec3() { return randVec3(0, RAND_MAX); }
	public static Vec4 randVec4() { return randVec4(0, RAND_MAX); }
	public static VecN randVecN(int n) { return randVecN(n, 0, RAND_MAX); }
	public static Vec2 randX(Vec2 x) { return randX(x, 0, RAND_MAX); }
	public static Vec3 randX(Vec3 x) { return randX(x, 0, RAND_MAX); }
	public static Vec4 randX(Vec4 x) { return randX(x, 0, RAND_MAX); }
	public static VecN randX(VecN x) { return randX(x, 0, RAND_MAX); }

	// fused multiply–add
	public static Vec2 fma(Vec2 a, Vec2 b, Vec2 c) { return _vec2(_fma(a.d, b.d, c.d)); }
	public static Vec3 fma(Vec3 a, Vec3 b, Vec3 c) { return _vec3(_fma(a.d, b.d, c.d)); }
	public static Vec4 fma(Vec4 a, Vec4 b, Vec4 c) { return _vec4(_fma(a.d, b.d, c.d)); }
	public static VecN fma(VecN a, VecN b, VecN c) { return _vecn(_fma(a.d, b.d, c.d)); }
	public static Vec2 fmaX(Vec2 a, Vec2 b, Vec2 c) { fma(a.d, b.d, c.d, a.d); return a; }
	public static Vec3 fmaX(Vec3 a, Vec3 b, Vec3 c) { fma(a.d, b.d, c.d, a.d); return a; }
	public static Vec4 fmaX(Vec4 a, Vec4 b, Vec4 c) { fma(a.d, b.d, c.d, a.d); return a; }
	public static VecN fmaX(VecN a, VecN b, VecN c) { fma(a.d, b.d, c.d, a.d); return a; }

	public static Vec2 fma(Vec2 a, Vec2 b) { return _vec2(_fma(a.d, b.d)); }
	public static Vec3 fma(Vec3 a, Vec3 b) { return _vec3(_fma(a.d, b.d)); }
	public static Vec4 fma(Vec4 a, Vec4 b) { return _vec4(_fma(a.d, b.d)); }
	public static VecN fma(VecN a, VecN b) { return _vecn(_fma(a.d, b.d)); }
	public static Vec2 fmaX(Vec2 a, Vec2 b) { fma(a.d, b.d, a.d); return a; }
	public static Vec3 fmaX(Vec3 a, Vec3 b) { fma(a.d, b.d, a.d); return a; }
	public static Vec4 fmaX(Vec4 a, Vec4 b) { fma(a.d, b.d, a.d); return a; }
	public static VecN fmaX(VecN a, VecN b) { fma(a.d, b.d, a.d); return a; }

	// https://thebookofshaders.com/glossary/?search=radians
	public static Vec2 radians(Vec2 degrees) { return _vec2(radians(degrees.d)); }
	public static Vec3 radians(Vec3 degrees) { return _vec3(radians(degrees.d)); }
	public static Vec4 radians(Vec4 degrees) { return _vec4(radians(degrees.d)); }
	public static VecN radians(VecN degrees) { return _vecn(radians(degrees.d)); }
	public static Vec2 radiansX(Vec2 degrees) { radians(degrees.d, degrees.d); return degrees; }
	public static Vec3 radiansX(Vec3 degrees) { radians(degrees.d, degrees.d); return degrees; }
	public static Vec4 radiansX(Vec4 degrees) { radians(degrees.d, degrees.d); return degrees; }
	public static VecN radiansX(VecN degrees) { radians(degrees.d, degrees.d); return degrees; }

	// https://thebookofshaders.com/glossary/?search=degrees
	public static Vec2 degrees(Vec2 radians) { return _vec2(degrees(radians.d)); }
	public static Vec3 degrees(Vec3 radians) { return _vec3(degrees(radians.d)); }
	public static Vec4 degrees(Vec4 radians) { return _vec4(degrees(radians.d)); }
	public static VecN degrees(VecN radians) { return _vecn(degrees(radians.d)); }
	public static Vec2 degreesX(Vec2 radians) { degrees(radians.d, radians.d); return radians; }
	public static Vec3 degreesX(Vec3 radians) { degrees(radians.d, radians.d); return radians; }
	public static Vec4 degreesX(Vec4 radians) { degrees(radians.d, radians.d); return radians; }
	public static VecN degreesX(VecN radians) { degrees(radians.d, radians.d); return radians; }

	// https://thebookofshaders.com/glossary/?search=sin
	public static Vec2 sin(Vec2 angle) { return _vec2(sin(angle.d)); }
	public static Vec3 sin(Vec3 angle) { return _vec3(sin(angle.d)); }
	public static Vec4 sin(Vec4 angle) { return _vec4(sin(angle.d)); }
	public static VecN sin(VecN angle) { return _vecn(sin(angle.d)); }
	public static Vec2 sinX(Vec2 angle) { sin(angle.d, angle.d); return angle; }
	public static Vec3 sinX(Vec3 angle) { sin(angle.d, angle.d); return angle; }
	public static Vec4 sinX(Vec4 angle) { sin(angle.d, angle.d); return angle; }
	public static VecN sinX(VecN angle) { sin(angle.d, angle.d); return angle; }

	// https://thebookofshaders.com/glossary/?search=cos
	public static Vec2 cos(Vec2 angle) { return _vec2(cos(angle.d)); }
	public static Vec3 cos(Vec3 angle) { return _vec3(cos(angle.d)); }
	public static Vec4 cos(Vec4 angle) { return _vec4(cos(angle.d)); }
	public static VecN cos(VecN angle) { return _vecn(cos(angle.d)); }
	public static Vec2 cosX(Vec2 angle) { cos(angle.d, angle.d); return angle; }
	public static Vec3 cosX(Vec3 angle) { cos(angle.d, angle.d); return angle; }
	public static Vec4 cosX(Vec4 angle) { cos(angle.d, angle.d); return angle; }
	public static VecN cosX(VecN angle) { cos(angle.d, angle.d); return angle; }

	// https://thebookofshaders.com/glossary/?search=tan
	public static Vec2 tan(Vec2 angle) { return _vec2(tan(angle.d)); }
	public static Vec3 tan(Vec3 angle) { return _vec3(tan(angle.d)); }
	public static Vec4 tan(Vec4 angle) { return _vec4(tan(angle.d)); }
	public static VecN tan(VecN angle) { return _vecn(tan(angle.d)); }
	public static Vec2 tanX(Vec2 angle) { tan(angle.d, angle.d); return angle; }
	public static Vec3 tanX(Vec3 angle) { tan(angle.d, angle.d); return angle; }
	public static Vec4 tanX(Vec4 angle) { tan(angle.d, angle.d); return angle; }
	public static VecN tanX(VecN angle) { tan(angle.d, angle.d); return angle; }

	// https://thebookofshaders.com/glossary/?search=asin
	public static Vec2 asin(Vec2 x) { return _vec2(asin(x.d)); }
	public static Vec3 asin(Vec3 x) { return _vec3(asin(x.d)); }
	public static Vec4 asin(Vec4 x) { return _vec4(asin(x.d)); }
	public static VecN asin(VecN x) { return _vecn(asin(x.d)); }
	public static Vec2 asinX(Vec2 x) { asin(x.d, x.d); return x; }
	public static Vec3 asinX(Vec3 x) { asin(x.d, x.d); return x; }
	public static Vec4 asinX(Vec4 x) { asin(x.d, x.d); return x; }
	public static VecN asinX(VecN x) { asin(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=acos
	public static Vec2 acos(Vec2 x) { return _vec2(acos(x.d)); }
	public static Vec3 acos(Vec3 x) { return _vec3(acos(x.d)); }
	public static Vec4 acos(Vec4 x) { return _vec4(acos(x.d)); }
	public static VecN acos(VecN x) { return _vecn(acos(x.d)); }
	public static Vec2 acosX(Vec2 x) { acos(x.d, x.d); return x; }
	public static Vec3 acosX(Vec3 x) { acos(x.d, x.d); return x; }
	public static Vec4 acosX(Vec4 x) { acos(x.d, x.d); return x; }
	public static VecN acosX(VecN x) { acos(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=atan
	public static Vec2 atan(Vec2 y_over_x) { return _vec2(atan(y_over_x.d)); }
	public static Vec3 atan(Vec3 y_over_x) { return _vec3(atan(y_over_x.d)); }
	public static Vec4 atan(Vec4 y_over_x) { return _vec4(atan(y_over_x.d)); }
	public static VecN atan(VecN y_over_x) { return _vecn(atan(y_over_x.d)); }
	public static Vec2 atanX(Vec2 y_over_x) { atan(y_over_x.d, y_over_x.d); return y_over_x; }
	public static Vec3 atanX(Vec3 y_over_x) { atan(y_over_x.d, y_over_x.d); return y_over_x; }
	public static Vec4 atanX(Vec4 y_over_x) { atan(y_over_x.d, y_over_x.d); return y_over_x; }
	public static VecN atanX(VecN y_over_x) { atan(y_over_x.d, y_over_x.d); return y_over_x; }
	public static Vec2 atan(Vec2 y, Vec2 x) { return _vec2(atanYX(y.d, x.d)); }
	public static Vec3 atan(Vec3 y, Vec3 x) { return _vec3(atanYX(y.d, x.d)); }
	public static Vec4 atan(Vec4 y, Vec4 x) { return _vec4(atanYX(y.d, x.d)); }
	public static VecN atan(VecN y, VecN x) { return _vecn(atanYX(y.d, x.d)); }
	public static Vec2 atanX(Vec2 y, Vec2 x) { atanYX(y.d, x.d, y.d); return y; }
	public static Vec3 atanX(Vec3 y, Vec3 x) { atanYX(y.d, x.d, y.d); return y; }
	public static Vec4 atanX(Vec4 y, Vec4 x) { atanYX(y.d, x.d, y.d); return y; }
	public static VecN atanX(VecN y, VecN x) { atanYX(y.d, x.d, y.d); return y; }

	public static Vec2 atan2(Vec2 y, Vec2 x) { return _vec2(atan2(y.d, x.d)); }
	public static Vec3 atan2(Vec3 y, Vec3 x) { return _vec3(atan2(y.d, x.d)); }
	public static Vec4 atan2(Vec4 y, Vec4 x) { return _vec4(atan2(y.d, x.d)); }
	public static VecN atan2(VecN y, VecN x) { return _vecn(atan2(y.d, x.d)); }
	public static Vec2 atan2X(Vec2 y, Vec2 x) { atan2(y.d, x.d, y.d); return y; }
	public static Vec3 atan2X(Vec3 y, Vec3 x) { atan2(y.d, x.d, y.d); return y; }
	public static Vec4 atan2X(Vec4 y, Vec4 x) { atan2(y.d, x.d, y.d); return y; }
	public static VecN atan2X(VecN y, VecN x) { atan2(y.d, x.d, y.d); return y; }

	// https://thebookofshaders.com/glossary/?search=pow
	public static Vec2 pow(Vec2 x, Vec2 y) { return _vec2(pow(x.d, y.d)); }
	public static Vec3 pow(Vec3 x, Vec3 y) { return _vec3(pow(x.d, y.d)); }
	public static Vec4 pow(Vec4 x, Vec4 y) { return _vec4(pow(x.d, y.d)); }
	public static VecN pow(VecN x, VecN y) { return _vecn(pow(x.d, y.d)); }
	public static Vec2 powX(Vec2 x, Vec2 y) { pow(x.d, y.d, x.d); return x; }
	public static Vec3 powX(Vec3 x, Vec3 y) { pow(x.d, y.d, x.d); return x; }
	public static Vec4 powX(Vec4 x, Vec4 y) { pow(x.d, y.d, x.d); return x; }
	public static VecN powX(VecN x, VecN y) { pow(x.d, y.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=exp
	public static Vec2 exp(Vec2 x) { return _vec2(exp(x.d)); }
	public static Vec3 exp(Vec3 x) { return _vec3(exp(x.d)); }
	public static Vec4 exp(Vec4 x) { return _vec4(exp(x.d)); }
	public static VecN exp(VecN x) { return _vecn(exp(x.d)); }
	public static Vec2 expX(Vec2 x) { exp(x.d, x.d); return x; }
	public static Vec3 expX(Vec3 x) { exp(x.d, x.d); return x; }
	public static Vec4 expX(Vec4 x) { exp(x.d, x.d); return x; }
	public static VecN expX(VecN x) { exp(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=log
	public static Vec2 log(Vec2 x) { return _vec2(log(x.d)); }
	public static Vec3 log(Vec3 x) { return _vec3(log(x.d)); }
	public static Vec4 log(Vec4 x) { return _vec4(log(x.d)); }
	public static VecN log(VecN x) { return _vecn(log(x.d)); }
	public static Vec2 logX(Vec2 x) { log(x.d, x.d); return x; }
	public static Vec3 logX(Vec3 x) { log(x.d, x.d); return x; }
	public static Vec4 logX(Vec4 x) { log(x.d, x.d); return x; }
	public static VecN logX(VecN x) { log(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=exp2
	public static Vec2 exp2(Vec2 x) { return _vec2(exp2(x.d)); }
	public static Vec3 exp2(Vec3 x) { return _vec3(exp2(x.d)); }
	public static Vec4 exp2(Vec4 x) { return _vec4(exp2(x.d)); }
	public static VecN exp2(VecN x) { return _vecn(exp2(x.d)); }
	public static Vec2 exp2X(Vec2 x) { exp2(x.d, x.d); return x; }
	public static Vec3 exp2X(Vec3 x) { exp2(x.d, x.d); return x; }
	public static Vec4 exp2X(Vec4 x) { exp2(x.d, x.d); return x; }
	public static VecN exp2X(VecN x) { exp2(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=log2
	public static Vec2 log2(Vec2 x) { return _vec2(log2(x.d)); }
	public static Vec3 log2(Vec3 x) { return _vec3(log2(x.d)); }
	public static Vec4 log2(Vec4 x) { return _vec4(log2(x.d)); }
	public static VecN log2(VecN x) { return _vecn(log2(x.d)); }
	public static Vec2 log2X(Vec2 x) { log2(x.d, x.d); return x; }
	public static Vec3 log2X(Vec3 x) { log2(x.d, x.d); return x; }
	public static Vec4 log2X(Vec4 x) { log2(x.d, x.d); return x; }
	public static VecN log2X(VecN x) { log2(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=sqrt
	public static Vec2 sqrt(Vec2 x) { return _vec2(sqrt(x.d)); }
	public static Vec3 sqrt(Vec3 x) { return _vec3(sqrt(x.d)); }
	public static Vec4 sqrt(Vec4 x) { return _vec4(sqrt(x.d)); }
	public static VecN sqrt(VecN x) { return _vecn(sqrt(x.d)); }
	public static Vec2 sqrtX(Vec2 x) { sqrt(x.d, x.d); return x; }
	public static Vec3 sqrtX(Vec3 x) { sqrt(x.d, x.d); return x; }
	public static Vec4 sqrtX(Vec4 x) { sqrt(x.d, x.d); return x; }
	public static VecN sqrtX(VecN x) { sqrt(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=inversesqrt
	public static Vec2 inversesqrt(Vec2 x) { return _vec2(inversesqrt(x.d)); }
	public static Vec3 inversesqrt(Vec3 x) { return _vec3(inversesqrt(x.d)); }
	public static Vec4 inversesqrt(Vec4 x) { return _vec4(inversesqrt(x.d)); }
	public static VecN inversesqrt(VecN x) { return _vecn(inversesqrt(x.d)); }
	public static Vec2 inversesqrtX(Vec2 x) { inversesqrt(x.d, x.d); return x; }
	public static Vec3 inversesqrtX(Vec3 x) { inversesqrt(x.d, x.d); return x; }
	public static Vec4 inversesqrtX(Vec4 x) { inversesqrt(x.d, x.d); return x; }
	public static VecN inversesqrtX(VecN x) { inversesqrt(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=abs
	public static Vec2 abs(Vec2 x) { return _vec2(abs(x.d)); }
	public static Vec3 abs(Vec3 x) { return _vec3(abs(x.d)); }
	public static Vec4 abs(Vec4 x) { return _vec4(abs(x.d)); }
	public static VecN abs(VecN x) { return _vecn(abs(x.d)); }
	public static Vec2 absX(Vec2 x) { abs(x.d, x.d); return x; }
	public static Vec3 absX(Vec3 x) { abs(x.d, x.d); return x; }
	public static Vec4 absX(Vec4 x) { abs(x.d, x.d); return x; }
	public static VecN absX(VecN x) { abs(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=sign
	public static Vec2 sign(Vec2 x) { return _vec2(sign(x.d)); }
	public static Vec3 sign(Vec3 x) { return _vec3(sign(x.d)); }
	public static Vec4 sign(Vec4 x) { return _vec4(sign(x.d)); }
	public static VecN sign(VecN x) { return _vecn(sign(x.d)); }
	public static Vec2 signX(Vec2 x) { sign(x.d, x.d); return x; }
	public static Vec3 signX(Vec3 x) { sign(x.d, x.d); return x; }
	public static Vec4 signX(Vec4 x) { sign(x.d, x.d); return x; }
	public static VecN signX(VecN x) { sign(x.d, x.d); return x; }

	public static Vec2 nonZeroSign(Vec2 x) { return _vec2(nonZeroSign(x.d)); }
	public static Vec3 nonZeroSign(Vec3 x) { return _vec3(nonZeroSign(x.d)); }
	public static Vec4 nonZeroSign(Vec4 x) { return _vec4(nonZeroSign(x.d)); }
	public static VecN nonZeroSign(VecN x) { return _vecn(nonZeroSign(x.d)); }
	public static Vec2 nonZeroSignX(Vec2 x) { nonZeroSign(x.d, x.d); return x; }
	public static Vec3 nonZeroSignX(Vec3 x) { nonZeroSign(x.d, x.d); return x; }
	public static Vec4 nonZeroSignX(Vec4 x) { nonZeroSign(x.d, x.d); return x; }
	public static VecN nonZeroSignX(VecN x) { nonZeroSign(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=floor
	public static Vec2 floor(Vec2 x) { return _vec2(floor(x.d)); }
	public static Vec3 floor(Vec3 x) { return _vec3(floor(x.d)); }
	public static Vec4 floor(Vec4 x) { return _vec4(floor(x.d)); }
	public static VecN floor(VecN x) { return _vecn(floor(x.d)); }
	public static Vec2 floorX(Vec2 x) { floor(x.d, x.d); return x; }
	public static Vec3 floorX(Vec3 x) { floor(x.d, x.d); return x; }
	public static Vec4 floorX(Vec4 x) { floor(x.d, x.d); return x; }
	public static VecN floorX(VecN x) { floor(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=ceil
	public static Vec2 ceil(Vec2 x) { return _vec2(ceil(x.d)); }
	public static Vec3 ceil(Vec3 x) { return _vec3(ceil(x.d)); }
	public static Vec4 ceil(Vec4 x) { return _vec4(ceil(x.d)); }
	public static VecN ceil(VecN x) { return _vecn(ceil(x.d)); }
	public static Vec2 ceilX(Vec2 x) { ceil(x.d, x.d); return x; }
	public static Vec3 ceilX(Vec3 x) { ceil(x.d, x.d); return x; }
	public static Vec4 ceilX(Vec4 x) { ceil(x.d, x.d); return x; }
	public static VecN ceilX(VecN x) { ceil(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=fract
	public static Vec2 fract(Vec2 x) { return _vec2(fract(x.d)); }
	public static Vec3 fract(Vec3 x) { return _vec3(fract(x.d)); }
	public static Vec4 fract(Vec4 x) { return _vec4(fract(x.d)); }
	public static VecN fract(VecN x) { return _vecn(fract(x.d)); }
	public static Vec2 fractX(Vec2 x) { fract(x.d, x.d); return x; }
	public static Vec3 fractX(Vec3 x) { fract(x.d, x.d); return x; }
	public static Vec4 fractX(Vec4 x) { fract(x.d, x.d); return x; }
	public static VecN fractX(VecN x) { fract(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=min
	public static Vec2 min(Vec2 x, Vec2 y) { return _vec2(min(x.d, y.d)); }
	public static Vec3 min(Vec3 x, Vec3 y) { return _vec3(min(x.d, y.d)); }
	public static Vec4 min(Vec4 x, Vec4 y) { return _vec4(min(x.d, y.d)); }
	public static VecN min(VecN x, VecN y) { return _vecn(min(x.d, y.d)); }
	public static Vec2 minX(Vec2 x, Vec2 y) { min(x.d, y.d, x.d); return x; }
	public static Vec3 minX(Vec3 x, Vec3 y) { min(x.d, y.d, x.d); return x; }
	public static Vec4 minX(Vec4 x, Vec4 y) { min(x.d, y.d, x.d); return x; }
	public static VecN minX(VecN x, VecN y) { min(x.d, y.d, x.d); return x; }
	public static Vec2 min(Vec2 x, double y) { return _vec2(min(x.d, y)); }
	public static Vec3 min(Vec3 x, double y) { return _vec3(min(x.d, y)); }
	public static Vec4 min(Vec4 x, double y) { return _vec4(min(x.d, y)); }
	public static VecN min(VecN x, double y) { return _vecn(min(x.d, y)); }
	public static Vec2 minX(Vec2 x, double y) { min(x.d, y, x.d); return x; }
	public static Vec3 minX(Vec3 x, double y) { min(x.d, y, x.d); return x; }
	public static Vec4 minX(Vec4 x, double y) { min(x.d, y, x.d); return x; }
	public static VecN minX(VecN x, double y) { min(x.d, y, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=max
	public static Vec2 max(Vec2 x, Vec2 y) { return _vec2(max(x.d, y.d)); }
	public static Vec3 max(Vec3 x, Vec3 y) { return _vec3(max(x.d, y.d)); }
	public static Vec4 max(Vec4 x, Vec4 y) { return _vec4(max(x.d, y.d)); }
	public static VecN max(VecN x, VecN y) { return _vecn(max(x.d, y.d)); }
	public static Vec2 maxX(Vec2 x, Vec2 y) { max(x.d, y.d, x.d); return x; }
	public static Vec3 maxX(Vec3 x, Vec3 y) { max(x.d, y.d, x.d); return x; }
	public static Vec4 maxX(Vec4 x, Vec4 y) { max(x.d, y.d, x.d); return x; }
	public static VecN maxX(VecN x, VecN y) { max(x.d, y.d, x.d); return x; }
	public static Vec2 max(Vec2 x, double y) { return _vec2(max(x.d, y)); }
	public static Vec3 max(Vec3 x, double y) { return _vec3(max(x.d, y)); }
	public static Vec4 max(Vec4 x, double y) { return _vec4(max(x.d, y)); }
	public static VecN max(VecN x, double y) { return _vecn(max(x.d, y)); }
	public static Vec2 maxX(Vec2 x, double y) { max(x.d, y, x.d); return x; }
	public static Vec3 maxX(Vec3 x, double y) { max(x.d, y, x.d); return x; }
	public static Vec4 maxX(Vec4 x, double y) { max(x.d, y, x.d); return x; }
	public static VecN maxX(VecN x, double y) { max(x.d, y, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=clamp
	public static Vec2 clamp(Vec2 x, Vec2 minVal, Vec2 maxVal) { return _vec2(clamp(x.d, minVal.d, maxVal.d)); }
	public static Vec3 clamp(Vec3 x, Vec3 minVal, Vec3 maxVal) { return _vec3(clamp(x.d, minVal.d, maxVal.d)); }
	public static Vec4 clamp(Vec4 x, Vec4 minVal, Vec4 maxVal) { return _vec4(clamp(x.d, minVal.d, maxVal.d)); }
	public static VecN clamp(VecN x, VecN minVal, VecN maxVal) { return _vecn(clamp(x.d, minVal.d, maxVal.d)); }
	public static Vec2 clampX(Vec2 x, Vec2 minVal, Vec2 maxVal) { clamp(x.d, minVal.d, maxVal.d, x.d); return x; }
	public static Vec3 clampX(Vec3 x, Vec3 minVal, Vec3 maxVal) { clamp(x.d, minVal.d, maxVal.d, x.d); return x; }
	public static Vec4 clampX(Vec4 x, Vec4 minVal, Vec4 maxVal) { clamp(x.d, minVal.d, maxVal.d, x.d); return x; }
	public static VecN clampX(VecN x, VecN minVal, VecN maxVal) { clamp(x.d, minVal.d, maxVal.d, x.d); return x; }
	public static Vec2 clamp(Vec2 x, double minVal, double maxVal) { return _vec2(clamp(x.d, minVal, maxVal)); }
	public static Vec3 clamp(Vec3 x, double minVal, double maxVal) { return _vec3(clamp(x.d, minVal, maxVal)); }
	public static Vec4 clamp(Vec4 x, double minVal, double maxVal) { return _vec4(clamp(x.d, minVal, maxVal)); }
	public static VecN clamp(VecN x, double minVal, double maxVal) { return _vecn(clamp(x.d, minVal, maxVal)); }
	public static Vec2 clampX(Vec2 x, double minVal, double maxVal) { clamp(x.d, minVal, maxVal, x.d); return x; }
	public static Vec3 clampX(Vec3 x, double minVal, double maxVal) { clamp(x.d, minVal, maxVal, x.d); return x; }
	public static Vec4 clampX(Vec4 x, double minVal, double maxVal) { clamp(x.d, minVal, maxVal, x.d); return x; }
	public static VecN clampX(VecN x, double minVal, double maxVal) { clamp(x.d, minVal, maxVal, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=mix
	public static Vec2 mix(Vec2 x, Vec2 y, Vec2 a) { return _vec2(mix(x.d, y.d, a.d)); }
	public static Vec3 mix(Vec3 x, Vec3 y, Vec3 a) { return _vec3(mix(x.d, y.d, a.d)); }
	public static Vec4 mix(Vec4 x, Vec4 y, Vec4 a) { return _vec4(mix(x.d, y.d, a.d)); }
	public static VecN mix(VecN x, VecN y, VecN a) { return _vecn(mix(x.d, y.d, a.d)); }
	public static Vec2 mixX(Vec2 x, Vec2 y, Vec2 a) { mix(x.d, y.d, a.d, x.d); return x; }
	public static Vec3 mixX(Vec3 x, Vec3 y, Vec3 a) { mix(x.d, y.d, a.d, x.d); return x; }
	public static Vec4 mixX(Vec4 x, Vec4 y, Vec4 a) { mix(x.d, y.d, a.d, x.d); return x; }
	public static VecN mixX(VecN x, VecN y, VecN a) { mix(x.d, y.d, a.d, x.d); return x; }
	public static Vec2 mix(Vec2 x, Vec2 y, double a) { return _vec2(mix(x.d, y.d, a)); }
	public static Vec3 mix(Vec3 x, Vec3 y, double a) { return _vec3(mix(x.d, y.d, a)); }
	public static Vec4 mix(Vec4 x, Vec4 y, double a) { return _vec4(mix(x.d, y.d, a)); }
	public static VecN mix(VecN x, VecN y, double a) { return _vecn(mix(x.d, y.d, a)); }
	public static Vec2 mixX(Vec2 x, Vec2 y, double a) { mix(x.d, y.d, a, x.d); return x; }
	public static Vec3 mixX(Vec3 x, Vec3 y, double a) { mix(x.d, y.d, a, x.d); return x; }
	public static Vec4 mixX(Vec4 x, Vec4 y, double a) { mix(x.d, y.d, a, x.d); return x; }
	public static VecN mixX(VecN x, VecN y, double a) { mix(x.d, y.d, a, x.d); return x; }

	// https://thebookofshaders.com/glossarx/?search=step
	public static Vec2 step(Vec2 edge, Vec2 x) { return _vec2(step(edge.d, x.d)); }
	public static Vec3 step(Vec3 edge, Vec3 x) { return _vec3(step(edge.d, x.d)); }
	public static Vec4 step(Vec4 edge, Vec4 x) { return _vec4(step(edge.d, x.d)); }
	public static VecN step(VecN edge, VecN x) { return _vecn(step(edge.d, x.d)); }
	public static Vec2 stepX(Vec2 edge, Vec2 x) { step(edge.d, x.d, edge.d); return edge; }
	public static Vec3 stepX(Vec3 edge, Vec3 x) { step(edge.d, x.d, edge.d); return edge; }
	public static Vec4 stepX(Vec4 edge, Vec4 x) { step(edge.d, x.d, edge.d); return edge; }
	public static VecN stepX(VecN edge, VecN x) { step(edge.d, x.d, edge.d); return edge; }
	public static Vec2 step(Vec2 edge, double x) { return _vec2(step(edge.d, x)); }
	public static Vec3 step(Vec3 edge, double x) { return _vec3(step(edge.d, x)); }
	public static Vec4 step(Vec4 edge, double x) { return _vec4(step(edge.d, x)); }
	public static VecN step(VecN edge, double x) { return _vecn(step(edge.d, x)); }
	public static Vec2 stepX(Vec2 edge, double x) { step(edge.d, x, edge.d); return edge; }
	public static Vec3 stepX(Vec3 edge, double x) { step(edge.d, x, edge.d); return edge; }
	public static Vec4 stepX(Vec4 edge, double x) { step(edge.d, x, edge.d); return edge; }
	public static VecN stepX(VecN edge, double x) { step(edge.d, x, edge.d); return edge; }

	// https://thebookofshaders.com/glossary/?search=smoothstep
	public static Vec2 smoothstep(Vec2 edge0, Vec2 edge1, Vec2 x) { return _vec2(smoothstep(edge0.d, edge1.d, x.d)); }
	public static Vec3 smoothstep(Vec3 edge0, Vec3 edge1, Vec3 x) { return _vec3(smoothstep(edge0.d, edge1.d, x.d)); }
	public static Vec4 smoothstep(Vec4 edge0, Vec4 edge1, Vec4 x) { return _vec4(smoothstep(edge0.d, edge1.d, x.d)); }
	public static VecN smoothstep(VecN edge0, VecN edge1, VecN x) { return _vecn(smoothstep(edge0.d, edge1.d, x.d)); }
	public static Vec2 smoothstepX(Vec2 edge0, Vec2 edge1, Vec2 x) { smoothstep(edge0.d, edge1.d, x.d, edge0.d); return edge0; }
	public static Vec3 smoothstepX(Vec3 edge0, Vec3 edge1, Vec3 x) { smoothstep(edge0.d, edge1.d, x.d, edge0.d); return edge0; }
	public static Vec4 smoothstepX(Vec4 edge0, Vec4 edge1, Vec4 x) { smoothstep(edge0.d, edge1.d, x.d, edge0.d); return edge0; }
	public static VecN smoothstepX(VecN edge0, VecN edge1, VecN x) { smoothstep(edge0.d, edge1.d, x.d, edge0.d); return edge0; }
	public static Vec2 smoothstep(Vec2 edge0, Vec2 edge1, double x) { return _vec2(smoothstep(edge0.d, edge1.d, x)); }
	public static Vec3 smoothstep(Vec3 edge0, Vec3 edge1, double x) { return _vec3(smoothstep(edge0.d, edge1.d, x)); }
	public static Vec4 smoothstep(Vec4 edge0, Vec4 edge1, double x) { return _vec4(smoothstep(edge0.d, edge1.d, x)); }
	public static VecN smoothstep(VecN edge0, VecN edge1, double x) { return _vecn(smoothstep(edge0.d, edge1.d, x)); }
	public static Vec2 smoothstepX(Vec2 edge0, Vec2 edge1, double x) { smoothstep(edge0.d, edge1.d, x, edge0.d); return edge0; }
	public static Vec3 smoothstepX(Vec3 edge0, Vec3 edge1, double x) { smoothstep(edge0.d, edge1.d, x, edge0.d); return edge0; }
	public static Vec4 smoothstepX(Vec4 edge0, Vec4 edge1, double x) { smoothstep(edge0.d, edge1.d, x, edge0.d); return edge0; }
	public static VecN smoothstepX(VecN edge0, VecN edge1, double x) { smoothstep(edge0.d, edge1.d, x, edge0.d); return edge0; }

	// Easing
	public static Vec2 ease(Vec2 x, Vec2 y, double t, double d, Easing easing) { return _vec2(ease(t, d, x.d, y.d, easing)); }
	public static Vec3 ease(Vec3 x, Vec3 y, double t, double d, Easing easing) { return _vec3(ease(t, d, x.d, y.d, easing)); }
	public static Vec4 ease(Vec4 x, Vec4 y, double t, double d, Easing easing) { return _vec4(ease(t, d, x.d, y.d, easing)); }
	public static VecN ease(VecN x, VecN y, double t, double d, Easing easing) { return _vecn(ease(t, d, x.d, y.d, easing)); }
	public static Vec2 easeX(Vec2 x, Vec2 y, double t, double d, Easing easing) { ease(t, d, x.d, y.d, easing, x.d); return x; }
	public static Vec3 easeX(Vec3 x, Vec3 y, double t, double d, Easing easing) { ease(t, d, x.d, y.d, easing, x.d); return x; }
	public static Vec4 easeX(Vec4 x, Vec4 y, double t, double d, Easing easing) { ease(t, d, x.d, y.d, easing, x.d); return x; }
	public static VecN easeX(VecN x, VecN y, double t, double d, Easing easing) { ease(t, d, x.d, y.d, easing, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=length
	public static double length(double[] x) { double sum = 0; for(double b : x) sum += pow(b, 2); return sqrt(sum); }
	public static double length(VecN x) { return length(x.d); }
	public static double length(MatMxN x) { return length(x.d); }

	// https://thebookofshaders.com/glossary/?search=distance
	public static Vec2 distance(Vec2 p0, Vec2 p1) { return _vec2(distance(p0.d, p1.d)); }
	public static Vec3 distance(Vec3 p0, Vec3 p1) { return _vec3(distance(p0.d, p1.d)); }
	public static Vec4 distance(Vec4 p0, Vec4 p1) { return _vec4(distance(p0.d, p1.d)); }
	public static VecN distance(VecN p0, VecN p1) { return _vecn(distance(p0.d, p1.d)); }
	public static Vec2 distanceX(Vec2 p0, Vec2 p1) { distance(p0.d, p1.d, p0.d); return p0; }
	public static Vec3 distanceX(Vec3 p0, Vec3 p1) { distance(p0.d, p1.d, p0.d); return p0; }
	public static Vec4 distanceX(Vec4 p0, Vec4 p1) { distance(p0.d, p1.d, p0.d); return p0; }
	public static VecN distanceX(VecN p0, VecN p1) { distance(p0.d, p1.d, p0.d); return p0; }
	public static Vec2 distance(Vec2 x) { return mul(x, sqrt(2)); }
	public static Vec3 distance(Vec3 x) { return mul(x, sqrt(2)); }
	public static Vec4 distance(Vec4 x) { return mul(x, sqrt(2)); }
	public static VecN distance(VecN x) { return mul(x, sqrt(2)); }
	public static Vec2 distanceX(Vec2 x) { return mulX(x, sqrt(2)); }
	public static Vec3 distanceX(Vec3 x) { return mulX(x, sqrt(2)); }
	public static Vec4 distanceX(Vec4 x) { return mulX(x, sqrt(2)); }
	public static VecN distanceX(VecN x) { return mulX(x, sqrt(2)); }

	// https://thebookofshaders.com/glossary/?search=dot
	public static double dot(VecN x, VecN y) { return dot(x.d, y.d); }
	public static double dot(MatMxN x, MatMxN y) { return dot(x.d, y.d); }
	public static double dot(VecN x, double... y) { return dot(x.d, y); }
	public static double dot(MatMxN x, double... y) { return dot(x.d, y); }

	// https://thebookofshaders.com/glossary/?search=cross
	public static Vec3 cross(Vec3 x, Vec3 y) { return vec3(cross(x.x(), x.y(), x.z(), y.x(), y.y(), y.z())); }
	public static Vec3 cross(Vec3 x, double yx, double yy, double yz) { return vec3(cross(x.x(), x.y(), x.z(), yx, yy, yz)); }
	public static Vec3 crossX(Vec3 x, Vec3 y) { cross(x.x(), x.y(), x.z(), y.x(), y.y(), y.z(), x.d); return x; }
	public static Vec3 crossX(Vec3 x, double yx, double yy, double yz) { cross(x.x(), x.y(), x.z(), yx, yy, yz, x.d); return x; }
	public static double cross(Vec2 x, Vec2 y) { return cross(x.x(), x.y(), y.x(), y.y()); }
	public static double cross(Vec2 x, double yx, double yy) { return cross(x.x(), x.y(), yx, yy); }

	// https://thebookofshaders.com/glossary/?search=normalize
	public static Vec2 normalize(Vec2 x) { return _vec2(normalize(x.d)); }
	public static Vec3 normalize(Vec3 x) { return _vec3(normalize(x.d)); }
	public static Vec4 normalize(Vec4 x) { return _vec4(normalize(x.d)); }
	public static VecN normalize(VecN x) { return _vecn(normalize(x.d)); }
	public static Vec2 normalizeX(Vec2 x) { normalize(x.d, x.d); return x; }
	public static Vec3 normalizeX(Vec3 x) { normalize(x.d, x.d); return x; }
	public static Vec4 normalizeX(Vec4 x) { normalize(x.d, x.d); return x; }
	public static VecN normalizeX(VecN x) { normalize(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=refract
	public static Vec2 refract(Vec2 I, Vec2 N, double eta) { return _vec2(refract(I.d, N.d, eta)); }
	public static Vec3 refract(Vec3 I, Vec3 N, double eta) { return _vec3(refract(I.d, N.d, eta)); }
	public static Vec4 refract(Vec4 I, Vec4 N, double eta) { return _vec4(refract(I.d, N.d, eta)); }
	public static VecN refract(VecN I, VecN N, double eta) { return _vecn(refract(I.d, N.d, eta)); }
	public static Vec2 refractX(Vec2 I, Vec2 N, double eta) { refract(I.d, N.d, eta, I.d); return I; }
	public static Vec3 refractX(Vec3 I, Vec3 N, double eta) { refract(I.d, N.d, eta, I.d); return I; }
	public static Vec4 refractX(Vec4 I, Vec4 N, double eta) { refract(I.d, N.d, eta, I.d); return I; }
	public static VecN refractX(VecN I, VecN N, double eta) { refract(I.d, N.d, eta, I.d); return I; }

	// Fold2
	public static Vec2 fold2(Vec2 fold) { return _vec2(fold2(fold.d)); }
	public static Vec3 fold2(Vec3 fold) { return _vec3(fold2(fold.d)); }
	public static Vec4 fold2(Vec4 fold) { return _vec4(fold2(fold.d)); }
	public static VecN fold2(VecN fold) { return _vecn(fold2(fold.d)); }
	public static Vec2 fold2X(Vec2 fold) { fold2(fold.d, fold.d); return fold; }
	public static Vec3 fold2X(Vec3 fold) { fold2(fold.d, fold.d); return fold; }
	public static Vec4 fold2X(Vec4 fold) { fold2(fold.d, fold.d); return fold; }
	public static VecN fold2X(VecN fold) { fold2(fold.d, fold.d); return fold; }

	// https://thebookofshaders.com/glossary/?search=matrixCompMult Maybe?
	// Please, Use another library. To achieve better performance.
	protected static Object multiplyByMatrix_2x2_2x2(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 2; final int nM1 = 2; final int mM2 = 2; final int nM2 = 2;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1[0] * m2[0] + m1[1] * m2[2];
				temp[1] = m1[0] * m2[1] + m1[1] * m2[3];
				temp[2] = m1[2] * m2[0] + m1[3] * m2[2];
				temp[3] = m1[2] * m2[1] + m1[3] * m2[3];
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(2);
				temp[1] = m1[0] * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(3);
				temp[2] = m1[2] * m2.getDoubleAt(0) + m1[3] * m2.getDoubleAt(2);
				temp[3] = m1[2] * m2.getDoubleAt(1) + m1[3] * m2.getDoubleAt(3);
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[2];
				temp[1] = m1.getDoubleAt(0) * m2[1] + m1.getDoubleAt(1) * m2[3];
				temp[2] = m1.getDoubleAt(2) * m2[0] + m1.getDoubleAt(3) * m2[2];
				temp[3] = m1.getDoubleAt(2) * m2[1] + m1.getDoubleAt(3) * m2[3];
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(2);
					temp[1] = m1.getDoubleAt(0) * m2.getDoubleAt(1) + m1.getDoubleAt(1) * m2.getDoubleAt(3);
					temp[2] = m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(2);
					temp[3] = m1.getDoubleAt(2) * m2.getDoubleAt(1) + m1.getDoubleAt(3) * m2.getDoubleAt(3);
				} else {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(4) + m1.getDoubleAt(1) * m2.getDoubleAt(6);
					temp[1] = m1.getDoubleAt(0) * m2.getDoubleAt(5) + m1.getDoubleAt(1) * m2.getDoubleAt(7);
					temp[2] = m1.getDoubleAt(2) * m2.getDoubleAt(4) + m1.getDoubleAt(3) * m2.getDoubleAt(6);
					temp[3] = m1.getDoubleAt(2) * m2.getDoubleAt(5) + m1.getDoubleAt(3) * m2.getDoubleAt(7);
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, m1[0] * m2[0] + m1[1] * m2[2]);
				temp.setVectorAt(1, m1[0] * m2[1] + m1[1] * m2[3]);
				temp.setVectorAt(2, m1[2] * m2[0] + m1[3] * m2[2]);
				temp.setVectorAt(3, m1[2] * m2[1] + m1[3] * m2[3]);
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(2));
					temp.setVectorAt(1, m1[0] * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(3));
					temp.setVectorAt(2, m1[2] * m2.getDoubleAt(0) + m1[3] * m2.getDoubleAt(2));
					temp.setVectorAt(3, m1[2] * m2.getDoubleAt(1) + m1[3] * m2.getDoubleAt(3));
				} else {
					temp.setVectorAt(4, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(2));
					temp.setVectorAt(5, m1[0] * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(3));
					temp.setVectorAt(6, m1[2] * m2.getDoubleAt(0) + m1[3] * m2.getDoubleAt(2));
					temp.setVectorAt(7, m1[2] * m2.getDoubleAt(1) + m1[3] * m2.getDoubleAt(3));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[2]);
					temp.setVectorAt(1, m1.getDoubleAt(0) * m2[1] + m1.getDoubleAt(1) * m2[3]);
					temp.setVectorAt(2, m1.getDoubleAt(2) * m2[0] + m1.getDoubleAt(3) * m2[2]);
					temp.setVectorAt(3, m1.getDoubleAt(2) * m2[1] + m1.getDoubleAt(3) * m2[3]);
				} else {
					temp.setVectorAt(4, m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[2]);
					temp.setVectorAt(5, m1.getDoubleAt(0) * m2[1] + m1.getDoubleAt(1) * m2[3]);
					temp.setVectorAt(6, m1.getDoubleAt(2) * m2[0] + m1.getDoubleAt(3) * m2[2]);
					temp.setVectorAt(7, m1.getDoubleAt(2) * m2[1] + m1.getDoubleAt(3) * m2[3]);
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(2));
						temp.setVectorAt(1, m1.getDoubleAt(0) * m2.getDoubleAt(1) + m1.getDoubleAt(1) * m2.getDoubleAt(3));
						temp.setVectorAt(2, m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(2));
						temp.setVectorAt(3, m1.getDoubleAt(2) * m2.getDoubleAt(1) + m1.getDoubleAt(3) * m2.getDoubleAt(3));
					} else {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(4) + m1.getDoubleAt(1) * m2.getDoubleAt(6));
						temp.setVectorAt(1, m1.getDoubleAt(0) * m2.getDoubleAt(5) + m1.getDoubleAt(1) * m2.getDoubleAt(7));
						temp.setVectorAt(2, m1.getDoubleAt(2) * m2.getDoubleAt(4) + m1.getDoubleAt(3) * m2.getDoubleAt(6));
						temp.setVectorAt(3, m1.getDoubleAt(2) * m2.getDoubleAt(5) + m1.getDoubleAt(3) * m2.getDoubleAt(7));
					}
				} else if(m2 != temp || m1 != temp) {
					temp.setVectorAt(4, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(2));
					temp.setVectorAt(5, m1.getDoubleAt(0) * m2.getDoubleAt(1) + m1.getDoubleAt(1) * m2.getDoubleAt(3));
					temp.setVectorAt(6, m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(2));
					temp.setVectorAt(7, m1.getDoubleAt(2) * m2.getDoubleAt(1) + m1.getDoubleAt(3) * m2.getDoubleAt(3));
				} else {
					temp.setVectorAt(8, m1.getDoubleAt(0) * m2.getDoubleAt(4) + m1.getDoubleAt(1) * m2.getDoubleAt(6));
					temp.setVectorAt(9, m1.getDoubleAt(0) * m2.getDoubleAt(5) + m1.getDoubleAt(1) * m2.getDoubleAt(7));
					temp.setVectorAt(10, m1.getDoubleAt(2) * m2.getDoubleAt(4) + m1.getDoubleAt(3) * m2.getDoubleAt(6));
					temp.setVectorAt(11, m1.getDoubleAt(2) * m2.getDoubleAt(5) + m1.getDoubleAt(3) * m2.getDoubleAt(7));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_2x2_2x1(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 2; final int nM1 = 2; final int mM2 = 2; final int nM2 = 1;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1[0] * m2[0] + m1[1] * m2[1];
				temp[1] = m1[2] * m2[0] + m1[3] * m2[1];
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(1);
				temp[1] = m1[2] * m2.getDoubleAt(0) + m1[3] * m2.getDoubleAt(1);
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[1];
				temp[1] = m1.getDoubleAt(2) * m2[0] + m1.getDoubleAt(3) * m2[1];
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1);
					temp[1] = m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(1);
				} else {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(4) + m1.getDoubleAt(1) * m2.getDoubleAt(5);
					temp[1] = m1.getDoubleAt(2) * m2.getDoubleAt(4) + m1.getDoubleAt(3) * m2.getDoubleAt(5);
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, m1[0] * m2[0] + m1[1] * m2[1]);
				temp.setVectorAt(1, m1[2] * m2[0] + m1[3] * m2[1]);
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(1));
					temp.setVectorAt(1, m1[2] * m2.getDoubleAt(0) + m1[3] * m2.getDoubleAt(1));
				} else {
					temp.setVectorAt(2, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(1));
					temp.setVectorAt(3, m1[2] * m2.getDoubleAt(0) + m1[3] * m2.getDoubleAt(1));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[1]);
					temp.setVectorAt(1, m1.getDoubleAt(2) * m2[0] + m1.getDoubleAt(3) * m2[1]);
				} else {
					temp.setVectorAt(4, m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[1]);
					temp.setVectorAt(5, m1.getDoubleAt(2) * m2[0] + m1.getDoubleAt(3) * m2[1]);
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1));
						temp.setVectorAt(1, m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(1));
					} else {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(4) + m1.getDoubleAt(1) * m2.getDoubleAt(5));
						temp.setVectorAt(1, m1.getDoubleAt(2) * m2.getDoubleAt(4) + m1.getDoubleAt(3) * m2.getDoubleAt(5));
					}
				} else if(m1 != temp) {
					temp.setVectorAt(2, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1));
					temp.setVectorAt(3, m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(1));
				} else if(m2 != temp) {
					temp.setVectorAt(4, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1));
					temp.setVectorAt(5, m1.getDoubleAt(2) * m2.getDoubleAt(0) + m1.getDoubleAt(3) * m2.getDoubleAt(1));
				} else {
					temp.setVectorAt(6, m1.getDoubleAt(0) * m2.getDoubleAt(4) + m1.getDoubleAt(1) * m2.getDoubleAt(5));
					temp.setVectorAt(7, m1.getDoubleAt(2) * m2.getDoubleAt(4) + m1.getDoubleAt(3) * m2.getDoubleAt(5));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_3x3_3x3(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 3; final int nM1 = 3; final int mM2 = 3; final int nM2 = 3;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1[0] * m2[0] + m1[1] * m2[3] + m1[2] * m2[6];
				temp[1] = m1[0] * m2[1] + m1[1] * m2[4] + m1[2] * m2[7];
				temp[2] = m1[0] * m2[2] + m1[1] * m2[5] + m1[2] * m2[8];
				temp[3] = m1[3] * m2[0] + m1[4] * m2[3] + m1[5] * m2[6];
				temp[4] = m1[3] * m2[1] + m1[4] * m2[4] + m1[5] * m2[7];
				temp[5] = m1[3] * m2[2] + m1[4] * m2[5] + m1[5] * m2[8];
				temp[6] = m1[6] * m2[0] + m1[7] * m2[3] + m1[8] * m2[6];
				temp[7] = m1[6] * m2[1] + m1[7] * m2[4] + m1[8] * m2[7];
				temp[8] = m1[6] * m2[2] + m1[7] * m2[5] + m1[8] * m2[8];
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(3) + m1[2] * m2.getDoubleAt(6);
				temp[1] = m1[0] * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(4) + m1[2] * m2.getDoubleAt(7);
				temp[2] = m1[0] * m2.getDoubleAt(2) + m1[1] * m2.getDoubleAt(5) + m1[2] * m2.getDoubleAt(8);
				temp[3] = m1[3] * m2.getDoubleAt(0) + m1[4] * m2.getDoubleAt(3) + m1[5] * m2.getDoubleAt(6);
				temp[4] = m1[3] * m2.getDoubleAt(1) + m1[4] * m2.getDoubleAt(4) + m1[5] * m2.getDoubleAt(7);
				temp[5] = m1[3] * m2.getDoubleAt(2) + m1[4] * m2.getDoubleAt(5) + m1[5] * m2.getDoubleAt(8);
				temp[6] = m1[6] * m2.getDoubleAt(0) + m1[7] * m2.getDoubleAt(3) + m1[8] * m2.getDoubleAt(6);
				temp[7] = m1[6] * m2.getDoubleAt(1) + m1[7] * m2.getDoubleAt(4) + m1[8] * m2.getDoubleAt(7);
				temp[8] = m1[6] * m2.getDoubleAt(2) + m1[7] * m2.getDoubleAt(5) + m1[8] * m2.getDoubleAt(8);
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[3] + m1.getDoubleAt(2) * m2[6];
				temp[1] = m1.getDoubleAt(0) * m2[1] + m1.getDoubleAt(1) * m2[4] + m1.getDoubleAt(2) * m2[7];
				temp[2] = m1.getDoubleAt(0) * m2[2] + m1.getDoubleAt(1) * m2[5] + m1.getDoubleAt(2) * m2[8];
				temp[3] = m1.getDoubleAt(3) * m2[0] + m1.getDoubleAt(4) * m2[3] + m1.getDoubleAt(5) * m2[6];
				temp[4] = m1.getDoubleAt(3) * m2[1] + m1.getDoubleAt(4) * m2[4] + m1.getDoubleAt(5) * m2[7];
				temp[5] = m1.getDoubleAt(3) * m2[2] + m1.getDoubleAt(4) * m2[5] + m1.getDoubleAt(5) * m2[8];
				temp[6] = m1.getDoubleAt(6) * m2[0] + m1.getDoubleAt(7) * m2[3] + m1.getDoubleAt(8) * m2[6];
				temp[7] = m1.getDoubleAt(6) * m2[1] + m1.getDoubleAt(7) * m2[4] + m1.getDoubleAt(8) * m2[7];
				temp[8] = m1.getDoubleAt(6) * m2[2] + m1.getDoubleAt(7) * m2[5] + m1.getDoubleAt(8) * m2[8];
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(3) + m1.getDoubleAt(2) * m2.getDoubleAt(6);
					temp[1] = m1.getDoubleAt(0) * m2.getDoubleAt(1) + m1.getDoubleAt(1) * m2.getDoubleAt(4) + m1.getDoubleAt(2) * m2.getDoubleAt(7);
					temp[2] = m1.getDoubleAt(0) * m2.getDoubleAt(2) + m1.getDoubleAt(1) * m2.getDoubleAt(5) + m1.getDoubleAt(2) * m2.getDoubleAt(8);
					temp[3] = m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(3) + m1.getDoubleAt(5) * m2.getDoubleAt(6);
					temp[4] = m1.getDoubleAt(3) * m2.getDoubleAt(1) + m1.getDoubleAt(4) * m2.getDoubleAt(4) + m1.getDoubleAt(5) * m2.getDoubleAt(7);
					temp[5] = m1.getDoubleAt(3) * m2.getDoubleAt(2) + m1.getDoubleAt(4) * m2.getDoubleAt(5) + m1.getDoubleAt(5) * m2.getDoubleAt(8);
					temp[6] = m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(3) + m1.getDoubleAt(8) * m2.getDoubleAt(6);
					temp[7] = m1.getDoubleAt(6) * m2.getDoubleAt(1) + m1.getDoubleAt(7) * m2.getDoubleAt(4) + m1.getDoubleAt(8) * m2.getDoubleAt(7);
					temp[8] = m1.getDoubleAt(6) * m2.getDoubleAt(2) + m1.getDoubleAt(7) * m2.getDoubleAt(5) + m1.getDoubleAt(8) * m2.getDoubleAt(8);
				} else {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(9)  + m1.getDoubleAt(1) * m2.getDoubleAt(12) + m1.getDoubleAt(2) * m2.getDoubleAt(15);
					temp[1] = m1.getDoubleAt(0) * m2.getDoubleAt(10) + m1.getDoubleAt(1) * m2.getDoubleAt(13) + m1.getDoubleAt(2) * m2.getDoubleAt(16);
					temp[2] = m1.getDoubleAt(0) * m2.getDoubleAt(11) + m1.getDoubleAt(1) * m2.getDoubleAt(14) + m1.getDoubleAt(2) * m2.getDoubleAt(17);
					temp[3] = m1.getDoubleAt(3) * m2.getDoubleAt(9)  + m1.getDoubleAt(4) * m2.getDoubleAt(12) + m1.getDoubleAt(5) * m2.getDoubleAt(15);
					temp[4] = m1.getDoubleAt(3) * m2.getDoubleAt(10) + m1.getDoubleAt(4) * m2.getDoubleAt(13) + m1.getDoubleAt(5) * m2.getDoubleAt(16);
					temp[5] = m1.getDoubleAt(3) * m2.getDoubleAt(11) + m1.getDoubleAt(4) * m2.getDoubleAt(14) + m1.getDoubleAt(5) * m2.getDoubleAt(17);
					temp[6] = m1.getDoubleAt(6) * m2.getDoubleAt(9)  + m1.getDoubleAt(7) * m2.getDoubleAt(12) + m1.getDoubleAt(8) * m2.getDoubleAt(15);
					temp[7] = m1.getDoubleAt(6) * m2.getDoubleAt(10) + m1.getDoubleAt(7) * m2.getDoubleAt(13) + m1.getDoubleAt(8) * m2.getDoubleAt(16);
					temp[8] = m1.getDoubleAt(6) * m2.getDoubleAt(11) + m1.getDoubleAt(7) * m2.getDoubleAt(14) + m1.getDoubleAt(8) * m2.getDoubleAt(17);
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, m1[0] * m2[0] + m1[1] * m2[3] + m1[2] * m2[6]);
				temp.setVectorAt(1, m1[0] * m2[1] + m1[1] * m2[4] + m1[2] * m2[7]);
				temp.setVectorAt(2, m1[0] * m2[2] + m1[1] * m2[5] + m1[2] * m2[8]);
				temp.setVectorAt(3, m1[3] * m2[0] + m1[4] * m2[3] + m1[5] * m2[6]);
				temp.setVectorAt(4, m1[3] * m2[1] + m1[4] * m2[4] + m1[5] * m2[7]);
				temp.setVectorAt(5, m1[3] * m2[2] + m1[4] * m2[5] + m1[5] * m2[8]);
				temp.setVectorAt(6, m1[6] * m2[0] + m1[7] * m2[3] + m1[8] * m2[6]);
				temp.setVectorAt(7, m1[6] * m2[1] + m1[7] * m2[4] + m1[8] * m2[7]);
				temp.setVectorAt(8, m1[6] * m2[2] + m1[7] * m2[5] + m1[8] * m2[8]);
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(3) + m1[2] * m2.getDoubleAt(6));
					temp.setVectorAt(1, m1[0] * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(4) + m1[2] * m2.getDoubleAt(7));
					temp.setVectorAt(2, m1[0] * m2.getDoubleAt(2) + m1[1] * m2.getDoubleAt(5) + m1[2] * m2.getDoubleAt(8));
					temp.setVectorAt(3, m1[3] * m2.getDoubleAt(0) + m1[4] * m2.getDoubleAt(3) + m1[5] * m2.getDoubleAt(6));
					temp.setVectorAt(4, m1[3] * m2.getDoubleAt(1) + m1[4] * m2.getDoubleAt(4) + m1[5] * m2.getDoubleAt(7));
					temp.setVectorAt(5, m1[3] * m2.getDoubleAt(2) + m1[4] * m2.getDoubleAt(5) + m1[5] * m2.getDoubleAt(8));
					temp.setVectorAt(6, m1[6] * m2.getDoubleAt(0) + m1[7] * m2.getDoubleAt(3) + m1[8] * m2.getDoubleAt(6));
					temp.setVectorAt(7, m1[6] * m2.getDoubleAt(1) + m1[7] * m2.getDoubleAt(4) + m1[8] * m2.getDoubleAt(7));
					temp.setVectorAt(8, m1[6] * m2.getDoubleAt(2) + m1[7] * m2.getDoubleAt(5) + m1[8] * m2.getDoubleAt(8));
				} else {
					temp.setVectorAt(9 , m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(3) + m1[2] * m2.getDoubleAt(6));
					temp.setVectorAt(10, m1[0] * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(4) + m1[2] * m2.getDoubleAt(7));
					temp.setVectorAt(11, m1[0] * m2.getDoubleAt(2) + m1[1] * m2.getDoubleAt(5) + m1[2] * m2.getDoubleAt(8));
					temp.setVectorAt(12, m1[3] * m2.getDoubleAt(0) + m1[4] * m2.getDoubleAt(3) + m1[5] * m2.getDoubleAt(6));
					temp.setVectorAt(13, m1[3] * m2.getDoubleAt(1) + m1[4] * m2.getDoubleAt(4) + m1[5] * m2.getDoubleAt(7));
					temp.setVectorAt(14, m1[3] * m2.getDoubleAt(2) + m1[4] * m2.getDoubleAt(5) + m1[5] * m2.getDoubleAt(8));
					temp.setVectorAt(15, m1[6] * m2.getDoubleAt(0) + m1[7] * m2.getDoubleAt(3) + m1[8] * m2.getDoubleAt(6));
					temp.setVectorAt(16, m1[6] * m2.getDoubleAt(1) + m1[7] * m2.getDoubleAt(4) + m1[8] * m2.getDoubleAt(7));
					temp.setVectorAt(17, m1[6] * m2.getDoubleAt(2) + m1[7] * m2.getDoubleAt(5) + m1[8] * m2.getDoubleAt(8));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[3] + m1.getDoubleAt(2) * m2[6]);
					temp.setVectorAt(1, m1.getDoubleAt(0) * m2[1] + m1.getDoubleAt(1) * m2[4] + m1.getDoubleAt(2) * m2[7]);
					temp.setVectorAt(2, m1.getDoubleAt(0) * m2[2] + m1.getDoubleAt(1) * m2[5] + m1.getDoubleAt(2) * m2[8]);
					temp.setVectorAt(3, m1.getDoubleAt(3) * m2[0] + m1.getDoubleAt(4) * m2[3] + m1.getDoubleAt(5) * m2[6]);
					temp.setVectorAt(4, m1.getDoubleAt(3) * m2[1] + m1.getDoubleAt(4) * m2[4] + m1.getDoubleAt(5) * m2[7]);
					temp.setVectorAt(5, m1.getDoubleAt(3) * m2[2] + m1.getDoubleAt(4) * m2[5] + m1.getDoubleAt(5) * m2[8]);
					temp.setVectorAt(6, m1.getDoubleAt(6) * m2[0] + m1.getDoubleAt(7) * m2[3] + m1.getDoubleAt(8) * m2[6]);
					temp.setVectorAt(7, m1.getDoubleAt(6) * m2[1] + m1.getDoubleAt(7) * m2[4] + m1.getDoubleAt(8) * m2[7]);
					temp.setVectorAt(8, m1.getDoubleAt(6) * m2[2] + m1.getDoubleAt(7) * m2[5] + m1.getDoubleAt(8) * m2[8]);
				} else {
					temp.setVectorAt(9 , m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[3] + m1.getDoubleAt(2) * m2[6]);
					temp.setVectorAt(10, m1.getDoubleAt(0) * m2[1] + m1.getDoubleAt(1) * m2[4] + m1.getDoubleAt(2) * m2[7]);
					temp.setVectorAt(11, m1.getDoubleAt(0) * m2[2] + m1.getDoubleAt(1) * m2[5] + m1.getDoubleAt(2) * m2[8]);
					temp.setVectorAt(12, m1.getDoubleAt(3) * m2[0] + m1.getDoubleAt(4) * m2[3] + m1.getDoubleAt(5) * m2[6]);
					temp.setVectorAt(13, m1.getDoubleAt(3) * m2[1] + m1.getDoubleAt(4) * m2[4] + m1.getDoubleAt(5) * m2[7]);
					temp.setVectorAt(14, m1.getDoubleAt(3) * m2[2] + m1.getDoubleAt(4) * m2[5] + m1.getDoubleAt(5) * m2[8]);
					temp.setVectorAt(15, m1.getDoubleAt(6) * m2[0] + m1.getDoubleAt(7) * m2[3] + m1.getDoubleAt(8) * m2[6]);
					temp.setVectorAt(16, m1.getDoubleAt(6) * m2[1] + m1.getDoubleAt(7) * m2[4] + m1.getDoubleAt(8) * m2[7]);
					temp.setVectorAt(17, m1.getDoubleAt(6) * m2[2] + m1.getDoubleAt(7) * m2[5] + m1.getDoubleAt(8) * m2[8]);
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(3) + m1.getDoubleAt(2) * m2.getDoubleAt(6));
						temp.setVectorAt(1, m1.getDoubleAt(0) * m2.getDoubleAt(1) + m1.getDoubleAt(1) * m2.getDoubleAt(4) + m1.getDoubleAt(2) * m2.getDoubleAt(7));
						temp.setVectorAt(2, m1.getDoubleAt(0) * m2.getDoubleAt(2) + m1.getDoubleAt(1) * m2.getDoubleAt(5) + m1.getDoubleAt(2) * m2.getDoubleAt(8));
						temp.setVectorAt(3, m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(3) + m1.getDoubleAt(5) * m2.getDoubleAt(6));
						temp.setVectorAt(4, m1.getDoubleAt(3) * m2.getDoubleAt(1) + m1.getDoubleAt(4) * m2.getDoubleAt(4) + m1.getDoubleAt(5) * m2.getDoubleAt(7));
						temp.setVectorAt(5, m1.getDoubleAt(3) * m2.getDoubleAt(2) + m1.getDoubleAt(4) * m2.getDoubleAt(5) + m1.getDoubleAt(5) * m2.getDoubleAt(8));
						temp.setVectorAt(6, m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(3) + m1.getDoubleAt(8) * m2.getDoubleAt(6));
						temp.setVectorAt(7, m1.getDoubleAt(6) * m2.getDoubleAt(1) + m1.getDoubleAt(7) * m2.getDoubleAt(4) + m1.getDoubleAt(8) * m2.getDoubleAt(7));
						temp.setVectorAt(8, m1.getDoubleAt(6) * m2.getDoubleAt(2) + m1.getDoubleAt(7) * m2.getDoubleAt(5) + m1.getDoubleAt(8) * m2.getDoubleAt(8));
					} else {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(9)  + m1.getDoubleAt(1) * m2.getDoubleAt(12) + m1.getDoubleAt(2) * m2.getDoubleAt(15));
						temp.setVectorAt(1, m1.getDoubleAt(0) * m2.getDoubleAt(10) + m1.getDoubleAt(1) * m2.getDoubleAt(13) + m1.getDoubleAt(2) * m2.getDoubleAt(16));
						temp.setVectorAt(2, m1.getDoubleAt(0) * m2.getDoubleAt(11) + m1.getDoubleAt(1) * m2.getDoubleAt(14) + m1.getDoubleAt(2) * m2.getDoubleAt(17));
						temp.setVectorAt(3, m1.getDoubleAt(3) * m2.getDoubleAt(9)  + m1.getDoubleAt(4) * m2.getDoubleAt(12) + m1.getDoubleAt(5) * m2.getDoubleAt(15));
						temp.setVectorAt(4, m1.getDoubleAt(3) * m2.getDoubleAt(10) + m1.getDoubleAt(4) * m2.getDoubleAt(13) + m1.getDoubleAt(5) * m2.getDoubleAt(16));
						temp.setVectorAt(5, m1.getDoubleAt(3) * m2.getDoubleAt(11) + m1.getDoubleAt(4) * m2.getDoubleAt(14) + m1.getDoubleAt(5) * m2.getDoubleAt(17));
						temp.setVectorAt(6, m1.getDoubleAt(6) * m2.getDoubleAt(9)  + m1.getDoubleAt(7) * m2.getDoubleAt(12) + m1.getDoubleAt(8) * m2.getDoubleAt(15));
						temp.setVectorAt(7, m1.getDoubleAt(6) * m2.getDoubleAt(10) + m1.getDoubleAt(7) * m2.getDoubleAt(13) + m1.getDoubleAt(8) * m2.getDoubleAt(16));
						temp.setVectorAt(8, m1.getDoubleAt(6) * m2.getDoubleAt(11) + m1.getDoubleAt(7) * m2.getDoubleAt(14) + m1.getDoubleAt(8) * m2.getDoubleAt(17));
					}
				} else if(m2 != temp || m1 != temp) {
					temp.setVectorAt(9 , m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(3) + m1.getDoubleAt(2) * m2.getDoubleAt(6));
					temp.setVectorAt(10, m1.getDoubleAt(0) * m2.getDoubleAt(1) + m1.getDoubleAt(1) * m2.getDoubleAt(4) + m1.getDoubleAt(2) * m2.getDoubleAt(7));
					temp.setVectorAt(11, m1.getDoubleAt(0) * m2.getDoubleAt(2) + m1.getDoubleAt(1) * m2.getDoubleAt(5) + m1.getDoubleAt(2) * m2.getDoubleAt(8));
					temp.setVectorAt(12, m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(3) + m1.getDoubleAt(5) * m2.getDoubleAt(6));
					temp.setVectorAt(13, m1.getDoubleAt(3) * m2.getDoubleAt(1) + m1.getDoubleAt(4) * m2.getDoubleAt(4) + m1.getDoubleAt(5) * m2.getDoubleAt(7));
					temp.setVectorAt(14, m1.getDoubleAt(3) * m2.getDoubleAt(2) + m1.getDoubleAt(4) * m2.getDoubleAt(5) + m1.getDoubleAt(5) * m2.getDoubleAt(8));
					temp.setVectorAt(15, m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(3) + m1.getDoubleAt(8) * m2.getDoubleAt(6));
					temp.setVectorAt(16, m1.getDoubleAt(6) * m2.getDoubleAt(1) + m1.getDoubleAt(7) * m2.getDoubleAt(4) + m1.getDoubleAt(8) * m2.getDoubleAt(7));
					temp.setVectorAt(17, m1.getDoubleAt(6) * m2.getDoubleAt(2) + m1.getDoubleAt(7) * m2.getDoubleAt(5) + m1.getDoubleAt(8) * m2.getDoubleAt(8));
				} else {
					temp.setVectorAt(18, m1.getDoubleAt(0) * m2.getDoubleAt(9)  + m1.getDoubleAt(1) * m2.getDoubleAt(12) + m1.getDoubleAt(2) * m2.getDoubleAt(15));
					temp.setVectorAt(19, m1.getDoubleAt(0) * m2.getDoubleAt(10) + m1.getDoubleAt(1) * m2.getDoubleAt(13) + m1.getDoubleAt(2) * m2.getDoubleAt(16));
					temp.setVectorAt(20, m1.getDoubleAt(0) * m2.getDoubleAt(11) + m1.getDoubleAt(1) * m2.getDoubleAt(14) + m1.getDoubleAt(2) * m2.getDoubleAt(17));
					temp.setVectorAt(21, m1.getDoubleAt(3) * m2.getDoubleAt(9)  + m1.getDoubleAt(4) * m2.getDoubleAt(12) + m1.getDoubleAt(5) * m2.getDoubleAt(15));
					temp.setVectorAt(22, m1.getDoubleAt(3) * m2.getDoubleAt(10) + m1.getDoubleAt(4) * m2.getDoubleAt(13) + m1.getDoubleAt(5) * m2.getDoubleAt(16));
					temp.setVectorAt(23, m1.getDoubleAt(3) * m2.getDoubleAt(11) + m1.getDoubleAt(4) * m2.getDoubleAt(14) + m1.getDoubleAt(5) * m2.getDoubleAt(17));
					temp.setVectorAt(24, m1.getDoubleAt(6) * m2.getDoubleAt(9)  + m1.getDoubleAt(7) * m2.getDoubleAt(12) + m1.getDoubleAt(8) * m2.getDoubleAt(15));
					temp.setVectorAt(25, m1.getDoubleAt(6) * m2.getDoubleAt(10) + m1.getDoubleAt(7) * m2.getDoubleAt(13) + m1.getDoubleAt(8) * m2.getDoubleAt(16));
					temp.setVectorAt(26, m1.getDoubleAt(6) * m2.getDoubleAt(11) + m1.getDoubleAt(7) * m2.getDoubleAt(14) + m1.getDoubleAt(8) * m2.getDoubleAt(17));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_3x3_3x1(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 3; final int nM1 = 3; final int mM2 = 3; final int nM2 = 1;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1[0] * m2[0] + m1[1] * m2[1] + m1[2] * m2[2];
				temp[1] = m1[3] * m2[0] + m1[4] * m2[1] + m1[5] * m2[2];
				temp[2] = m1[6] * m2[0] + m1[7] * m2[1] + m1[8] * m2[2];
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(1) + m1[2] * m2.getDoubleAt(2);
				temp[1] = m1[3] * m2.getDoubleAt(0) + m1[4] * m2.getDoubleAt(1) + m1[5] * m2.getDoubleAt(2);
				temp[2] = m1[6] * m2.getDoubleAt(0) + m1[7] * m2.getDoubleAt(1) + m1[8] * m2.getDoubleAt(2);
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[1] + m1.getDoubleAt(2) * m2[2];
				temp[1] = m1.getDoubleAt(3) * m2[0] + m1.getDoubleAt(4) * m2[1] + m1.getDoubleAt(5) * m2[2];
				temp[2] = m1.getDoubleAt(6) * m2[0] + m1.getDoubleAt(7) * m2[1] + m1.getDoubleAt(8) * m2[2];
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1) + m1.getDoubleAt(2) * m2.getDoubleAt(2);
					temp[1] = m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(1) + m1.getDoubleAt(5) * m2.getDoubleAt(2);
					temp[2] = m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(1) + m1.getDoubleAt(8) * m2.getDoubleAt(2);
				} else {
					temp[0] = m1.getDoubleAt(0) * m2.getDoubleAt(9) + m1.getDoubleAt(1) * m2.getDoubleAt(10) + m1.getDoubleAt(2) * m2.getDoubleAt(11);
					temp[1] = m1.getDoubleAt(3) * m2.getDoubleAt(9) + m1.getDoubleAt(4) * m2.getDoubleAt(10) + m1.getDoubleAt(5) * m2.getDoubleAt(11);
					temp[2] = m1.getDoubleAt(6) * m2.getDoubleAt(9) + m1.getDoubleAt(7) * m2.getDoubleAt(10) + m1.getDoubleAt(8) * m2.getDoubleAt(11);
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, m1[0] * m2[0] + m1[1] * m2[1] + m1[2] * m2[2]);
				temp.setVectorAt(1, m1[3] * m2[0] + m1[4] * m2[1] + m1[5] * m2[2]);
				temp.setVectorAt(2, m1[6] * m2[0] + m1[7] * m2[1] + m1[8] * m2[2]);
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(1) + m1[2] * m2.getDoubleAt(2));
					temp.setVectorAt(1, m1[3] * m2.getDoubleAt(0) + m1[4] * m2.getDoubleAt(1) + m1[5] * m2.getDoubleAt(2));
					temp.setVectorAt(2, m1[6] * m2.getDoubleAt(0) + m1[7] * m2.getDoubleAt(1) + m1[8] * m2.getDoubleAt(2));
				} else {
					temp.setVectorAt(3, m1[0] * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(1) + m1[2] * m2.getDoubleAt(2));
					temp.setVectorAt(4, m1[3] * m2.getDoubleAt(0) + m1[4] * m2.getDoubleAt(1) + m1[5] * m2.getDoubleAt(2));
					temp.setVectorAt(5, m1[6] * m2.getDoubleAt(0) + m1[7] * m2.getDoubleAt(1) + m1[8] * m2.getDoubleAt(2));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[1] + m1.getDoubleAt(2) * m2[2]);
					temp.setVectorAt(1, m1.getDoubleAt(3) * m2[0] + m1.getDoubleAt(4) * m2[1] + m1.getDoubleAt(5) * m2[2]);
					temp.setVectorAt(2, m1.getDoubleAt(6) * m2[0] + m1.getDoubleAt(7) * m2[1] + m1.getDoubleAt(8) * m2[2]);
				} else {
					temp.setVectorAt(9 , m1.getDoubleAt(0) * m2[0] + m1.getDoubleAt(1) * m2[1] + m1.getDoubleAt(2) * m2[2]);
					temp.setVectorAt(10, m1.getDoubleAt(3) * m2[0] + m1.getDoubleAt(4) * m2[1] + m1.getDoubleAt(5) * m2[2]);
					temp.setVectorAt(11, m1.getDoubleAt(6) * m2[0] + m1.getDoubleAt(7) * m2[1] + m1.getDoubleAt(8) * m2[2]);
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1) + m1.getDoubleAt(2) * m2.getDoubleAt(2));
						temp.setVectorAt(1, m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(1) + m1.getDoubleAt(5) * m2.getDoubleAt(2));
						temp.setVectorAt(2, m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(1) + m1.getDoubleAt(8) * m2.getDoubleAt(2));
					} else {
						temp.setVectorAt(0, m1.getDoubleAt(0) * m2.getDoubleAt(9) + m1.getDoubleAt(1) * m2.getDoubleAt(10) + m1.getDoubleAt(2) * m2.getDoubleAt(11));
						temp.setVectorAt(1, m1.getDoubleAt(3) * m2.getDoubleAt(9) + m1.getDoubleAt(4) * m2.getDoubleAt(10) + m1.getDoubleAt(5) * m2.getDoubleAt(11));
						temp.setVectorAt(2, m1.getDoubleAt(6) * m2.getDoubleAt(9) + m1.getDoubleAt(7) * m2.getDoubleAt(10) + m1.getDoubleAt(8) * m2.getDoubleAt(11));
					}
				} else if(m1 != temp) {
					temp.setVectorAt(3, m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1) + m1.getDoubleAt(2) * m2.getDoubleAt(2));
					temp.setVectorAt(4, m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(1) + m1.getDoubleAt(5) * m2.getDoubleAt(2));
					temp.setVectorAt(5, m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(1) + m1.getDoubleAt(8) * m2.getDoubleAt(2));
				} else if(m2 != temp) {
					temp.setVectorAt(9 , m1.getDoubleAt(0) * m2.getDoubleAt(0) + m1.getDoubleAt(1) * m2.getDoubleAt(1) + m1.getDoubleAt(2) * m2.getDoubleAt(2));
					temp.setVectorAt(10, m1.getDoubleAt(3) * m2.getDoubleAt(0) + m1.getDoubleAt(4) * m2.getDoubleAt(1) + m1.getDoubleAt(5) * m2.getDoubleAt(2));
					temp.setVectorAt(11, m1.getDoubleAt(6) * m2.getDoubleAt(0) + m1.getDoubleAt(7) * m2.getDoubleAt(1) + m1.getDoubleAt(8) * m2.getDoubleAt(2));
				} else {
					temp.setVectorAt(12, m1.getDoubleAt(0) * m2.getDoubleAt(9) + m1.getDoubleAt(1) * m2.getDoubleAt(10) + m1.getDoubleAt(2) * m2.getDoubleAt(11));
					temp.setVectorAt(13, m1.getDoubleAt(3) * m2.getDoubleAt(9) + m1.getDoubleAt(4) * m2.getDoubleAt(10) + m1.getDoubleAt(5) * m2.getDoubleAt(11));
					temp.setVectorAt(14, m1.getDoubleAt(6) * m2.getDoubleAt(9) + m1.getDoubleAt(7) * m2.getDoubleAt(10) + m1.getDoubleAt(8) * m2.getDoubleAt(11));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_4x4_4x4(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 4; final int nM1 = 4; final int mM2 = 4; final int nM2 = 4;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0]  = m1[0]  * m2[0] + m1[1]  * m2[4] + m1[2]  * m2[8]  + m1[3]  * m2[12];
				temp[1]  = m1[0]  * m2[1] + m1[1]  * m2[5] + m1[2]  * m2[9]  + m1[3]  * m2[13];
				temp[2]  = m1[0]  * m2[2] + m1[1]  * m2[6] + m1[2]  * m2[10] + m1[3]  * m2[14];
				temp[3]  = m1[0]  * m2[3] + m1[1]  * m2[7] + m1[2]  * m2[11] + m1[3]  * m2[15];
				temp[4]  = m1[4]  * m2[0] + m1[5]  * m2[4] + m1[6]  * m2[8]  + m1[7]  * m2[12];
				temp[5]  = m1[4]  * m2[1] + m1[5]  * m2[5] + m1[6]  * m2[9]  + m1[7]  * m2[13];
				temp[6]  = m1[4]  * m2[2] + m1[5]  * m2[6] + m1[6]  * m2[10] + m1[7]  * m2[14];
				temp[7]  = m1[4]  * m2[3] + m1[5]  * m2[7] + m1[6]  * m2[11] + m1[7]  * m2[15];
				temp[8]  = m1[8]  * m2[0] + m1[9]  * m2[4] + m1[10] * m2[8]  + m1[11] * m2[12];
				temp[9]  = m1[8]  * m2[1] + m1[9]  * m2[5] + m1[10] * m2[9]  + m1[11] * m2[13];
				temp[10] = m1[8]  * m2[2] + m1[9]  * m2[6] + m1[10] * m2[10] + m1[11] * m2[14];
				temp[11] = m1[8]  * m2[3] + m1[9]  * m2[7] + m1[10] * m2[11] + m1[11] * m2[15];
				temp[12] = m1[12] * m2[0] + m1[13] * m2[4] + m1[14] * m2[8]  + m1[15] * m2[12];
				temp[13] = m1[12] * m2[1] + m1[13] * m2[5] + m1[14] * m2[9]  + m1[15] * m2[13];
				temp[14] = m1[12] * m2[2] + m1[13] * m2[6] + m1[14] * m2[10] + m1[15] * m2[14];
				temp[15] = m1[12] * m2[3] + m1[13] * m2[7] + m1[14] * m2[11] + m1[15] * m2[15];
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = m1[0]   * m2.getDoubleAt(0) + m1[1]  * m2.getDoubleAt(4) + m1[2]  * m2.getDoubleAt(8)  + m1[3]  * m2.getDoubleAt(12);
				temp[1] = m1[0]   * m2.getDoubleAt(1) + m1[1]  * m2.getDoubleAt(5) + m1[2]  * m2.getDoubleAt(9)  + m1[3]  * m2.getDoubleAt(13);
				temp[2] = m1[0]   * m2.getDoubleAt(2) + m1[1]  * m2.getDoubleAt(6) + m1[2]  * m2.getDoubleAt(10) + m1[3]  * m2.getDoubleAt(14);
				temp[3] = m1[0]   * m2.getDoubleAt(3) + m1[1]  * m2.getDoubleAt(7) + m1[2]  * m2.getDoubleAt(11) + m1[3]  * m2.getDoubleAt(15);
				temp[4] = m1[4]   * m2.getDoubleAt(0) + m1[5]  * m2.getDoubleAt(4) + m1[6]  * m2.getDoubleAt(8)  + m1[7]  * m2.getDoubleAt(12);
				temp[5] = m1[4]   * m2.getDoubleAt(1) + m1[5]  * m2.getDoubleAt(5) + m1[6]  * m2.getDoubleAt(9)  + m1[7]  * m2.getDoubleAt(13);
				temp[6] = m1[4]   * m2.getDoubleAt(2) + m1[5]  * m2.getDoubleAt(6) + m1[6]  * m2.getDoubleAt(10) + m1[7]  * m2.getDoubleAt(14);
				temp[7] = m1[4]   * m2.getDoubleAt(3) + m1[5]  * m2.getDoubleAt(7) + m1[6]  * m2.getDoubleAt(11) + m1[7]  * m2.getDoubleAt(15);
				temp[8] = m1[8]   * m2.getDoubleAt(0) + m1[9]  * m2.getDoubleAt(4) + m1[10] * m2.getDoubleAt(8)  + m1[11] * m2.getDoubleAt(12);
				temp[9] = m1[8]   * m2.getDoubleAt(1) + m1[9]  * m2.getDoubleAt(5) + m1[10] * m2.getDoubleAt(9)  + m1[11] * m2.getDoubleAt(13);
				temp[10] = m1[8]  * m2.getDoubleAt(2) + m1[9]  * m2.getDoubleAt(6) + m1[10] * m2.getDoubleAt(10) + m1[11] * m2.getDoubleAt(14);
				temp[11] = m1[8]  * m2.getDoubleAt(3) + m1[9]  * m2.getDoubleAt(7) + m1[10] * m2.getDoubleAt(11) + m1[11] * m2.getDoubleAt(15);
				temp[12] = m1[12] * m2.getDoubleAt(0) + m1[13] * m2.getDoubleAt(4) + m1[14] * m2.getDoubleAt(8)  + m1[15] * m2.getDoubleAt(12);
				temp[13] = m1[12] * m2.getDoubleAt(1) + m1[13] * m2.getDoubleAt(5) + m1[14] * m2.getDoubleAt(9)  + m1[15] * m2.getDoubleAt(13);
				temp[14] = m1[12] * m2.getDoubleAt(2) + m1[13] * m2.getDoubleAt(6) + m1[14] * m2.getDoubleAt(10) + m1[15] * m2.getDoubleAt(14);
				temp[15] = m1[12] * m2.getDoubleAt(3) + m1[13] * m2.getDoubleAt(7) + m1[14] * m2.getDoubleAt(11) + m1[15] * m2.getDoubleAt(15);
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1.getDoubleAt(0)   * m2[0] + m1.getDoubleAt(1)  * m2[4] + m1.getDoubleAt(2)  * m2[8]  + m1.getDoubleAt(3)  * m2[12];
				temp[1] = m1.getDoubleAt(0)   * m2[1] + m1.getDoubleAt(1)  * m2[5] + m1.getDoubleAt(2)  * m2[9]  + m1.getDoubleAt(3)  * m2[13];
				temp[2] = m1.getDoubleAt(0)   * m2[2] + m1.getDoubleAt(1)  * m2[6] + m1.getDoubleAt(2)  * m2[10] + m1.getDoubleAt(3)  * m2[14];
				temp[3] = m1.getDoubleAt(0)   * m2[3] + m1.getDoubleAt(1)  * m2[7] + m1.getDoubleAt(2)  * m2[11] + m1.getDoubleAt(3)  * m2[15];
				temp[4] = m1.getDoubleAt(4)   * m2[0] + m1.getDoubleAt(5)  * m2[4] + m1.getDoubleAt(6)  * m2[8]  + m1.getDoubleAt(7)  * m2[12];
				temp[5] = m1.getDoubleAt(4)   * m2[1] + m1.getDoubleAt(5)  * m2[5] + m1.getDoubleAt(6)  * m2[9]  + m1.getDoubleAt(7)  * m2[13];
				temp[6] = m1.getDoubleAt(4)   * m2[2] + m1.getDoubleAt(5)  * m2[6] + m1.getDoubleAt(6)  * m2[10] + m1.getDoubleAt(7)  * m2[14];
				temp[7] = m1.getDoubleAt(4)   * m2[3] + m1.getDoubleAt(5)  * m2[7] + m1.getDoubleAt(6)  * m2[11] + m1.getDoubleAt(7)  * m2[15];
				temp[8] = m1.getDoubleAt(8)   * m2[0] + m1.getDoubleAt(9)  * m2[4] + m1.getDoubleAt(10) * m2[8]  + m1.getDoubleAt(11) * m2[12];
				temp[9] = m1.getDoubleAt(8)   * m2[1] + m1.getDoubleAt(9)  * m2[5] + m1.getDoubleAt(10) * m2[9]  + m1.getDoubleAt(11) * m2[13];
				temp[10] = m1.getDoubleAt(8)  * m2[2] + m1.getDoubleAt(9)  * m2[6] + m1.getDoubleAt(10) * m2[10] + m1.getDoubleAt(11) * m2[14];
				temp[11] = m1.getDoubleAt(8)  * m2[3] + m1.getDoubleAt(9)  * m2[7] + m1.getDoubleAt(10) * m2[11] + m1.getDoubleAt(11) * m2[15];
				temp[12] = m1.getDoubleAt(12) * m2[0] + m1.getDoubleAt(13) * m2[4] + m1.getDoubleAt(14) * m2[8]  + m1.getDoubleAt(15) * m2[12];
				temp[13] = m1.getDoubleAt(12) * m2[1] + m1.getDoubleAt(13) * m2[5] + m1.getDoubleAt(14) * m2[9]  + m1.getDoubleAt(15) * m2[13];
				temp[14] = m1.getDoubleAt(12) * m2[2] + m1.getDoubleAt(13) * m2[6] + m1.getDoubleAt(14) * m2[10] + m1.getDoubleAt(15) * m2[14];
				temp[15] = m1.getDoubleAt(12) * m2[3] + m1.getDoubleAt(13) * m2[7] + m1.getDoubleAt(14) * m2[11] + m1.getDoubleAt(15) * m2[15];
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = m1.getDoubleAt(0)   * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(4) + m1.getDoubleAt(2)  * m2.getDoubleAt(8)  + m1.getDoubleAt(3)  * m2.getDoubleAt(12);
					temp[1] = m1.getDoubleAt(0)   * m2.getDoubleAt(1) + m1.getDoubleAt(1)  * m2.getDoubleAt(5) + m1.getDoubleAt(2)  * m2.getDoubleAt(9)  + m1.getDoubleAt(3)  * m2.getDoubleAt(13);
					temp[2] = m1.getDoubleAt(0)   * m2.getDoubleAt(2) + m1.getDoubleAt(1)  * m2.getDoubleAt(6) + m1.getDoubleAt(2)  * m2.getDoubleAt(10) + m1.getDoubleAt(3)  * m2.getDoubleAt(14);
					temp[3] = m1.getDoubleAt(0)   * m2.getDoubleAt(3) + m1.getDoubleAt(1)  * m2.getDoubleAt(7) + m1.getDoubleAt(2)  * m2.getDoubleAt(11) + m1.getDoubleAt(3)  * m2.getDoubleAt(15);
					temp[4] = m1.getDoubleAt(4)   * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(4) + m1.getDoubleAt(6)  * m2.getDoubleAt(8)  + m1.getDoubleAt(7)  * m2.getDoubleAt(12);
					temp[5] = m1.getDoubleAt(4)   * m2.getDoubleAt(1) + m1.getDoubleAt(5)  * m2.getDoubleAt(5) + m1.getDoubleAt(6)  * m2.getDoubleAt(9)  + m1.getDoubleAt(7)  * m2.getDoubleAt(13);
					temp[6] = m1.getDoubleAt(4)   * m2.getDoubleAt(2) + m1.getDoubleAt(5)  * m2.getDoubleAt(6) + m1.getDoubleAt(6)  * m2.getDoubleAt(10) + m1.getDoubleAt(7)  * m2.getDoubleAt(14);
					temp[7] = m1.getDoubleAt(4)   * m2.getDoubleAt(3) + m1.getDoubleAt(5)  * m2.getDoubleAt(7) + m1.getDoubleAt(6)  * m2.getDoubleAt(11) + m1.getDoubleAt(7)  * m2.getDoubleAt(15);
					temp[8] = m1.getDoubleAt(8)   * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(4) + m1.getDoubleAt(10) * m2.getDoubleAt(8)  + m1.getDoubleAt(11) * m2.getDoubleAt(12);
					temp[9] = m1.getDoubleAt(8)   * m2.getDoubleAt(1) + m1.getDoubleAt(9)  * m2.getDoubleAt(5) + m1.getDoubleAt(10) * m2.getDoubleAt(9)  + m1.getDoubleAt(11) * m2.getDoubleAt(13);
					temp[10] = m1.getDoubleAt(8)  * m2.getDoubleAt(2) + m1.getDoubleAt(9)  * m2.getDoubleAt(6) + m1.getDoubleAt(10) * m2.getDoubleAt(10) + m1.getDoubleAt(11) * m2.getDoubleAt(14);
					temp[11] = m1.getDoubleAt(8)  * m2.getDoubleAt(3) + m1.getDoubleAt(9)  * m2.getDoubleAt(7) + m1.getDoubleAt(10) * m2.getDoubleAt(11) + m1.getDoubleAt(11) * m2.getDoubleAt(15);
					temp[12] = m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(4) + m1.getDoubleAt(14) * m2.getDoubleAt(8)  + m1.getDoubleAt(15) * m2.getDoubleAt(12);
					temp[13] = m1.getDoubleAt(12) * m2.getDoubleAt(1) + m1.getDoubleAt(13) * m2.getDoubleAt(5) + m1.getDoubleAt(14) * m2.getDoubleAt(9)  + m1.getDoubleAt(15) * m2.getDoubleAt(13);
					temp[14] = m1.getDoubleAt(12) * m2.getDoubleAt(2) + m1.getDoubleAt(13) * m2.getDoubleAt(6) + m1.getDoubleAt(14) * m2.getDoubleAt(10) + m1.getDoubleAt(15) * m2.getDoubleAt(14);
					temp[15] = m1.getDoubleAt(12) * m2.getDoubleAt(3) + m1.getDoubleAt(13) * m2.getDoubleAt(7) + m1.getDoubleAt(14) * m2.getDoubleAt(11) + m1.getDoubleAt(15) * m2.getDoubleAt(15);
				} else {
					temp[0] = m1.getDoubleAt(0)   * m2.getDoubleAt(16) + m1.getDoubleAt(1)  * m2.getDoubleAt(20) + m1.getDoubleAt(2)  * m2.getDoubleAt(24) + m1.getDoubleAt(3)  * m2.getDoubleAt(28);
					temp[1] = m1.getDoubleAt(0)   * m2.getDoubleAt(17) + m1.getDoubleAt(1)  * m2.getDoubleAt(21) + m1.getDoubleAt(2)  * m2.getDoubleAt(25) + m1.getDoubleAt(3)  * m2.getDoubleAt(29);
					temp[2] = m1.getDoubleAt(0)   * m2.getDoubleAt(18) + m1.getDoubleAt(1)  * m2.getDoubleAt(22) + m1.getDoubleAt(2)  * m2.getDoubleAt(26) + m1.getDoubleAt(3)  * m2.getDoubleAt(30);
					temp[3] = m1.getDoubleAt(0)   * m2.getDoubleAt(19) + m1.getDoubleAt(1)  * m2.getDoubleAt(23) + m1.getDoubleAt(2)  * m2.getDoubleAt(27) + m1.getDoubleAt(3)  * m2.getDoubleAt(31);
					temp[4] = m1.getDoubleAt(4)   * m2.getDoubleAt(16) + m1.getDoubleAt(5)  * m2.getDoubleAt(20) + m1.getDoubleAt(6)  * m2.getDoubleAt(24) + m1.getDoubleAt(7)  * m2.getDoubleAt(28);
					temp[5] = m1.getDoubleAt(4)   * m2.getDoubleAt(17) + m1.getDoubleAt(5)  * m2.getDoubleAt(21) + m1.getDoubleAt(6)  * m2.getDoubleAt(25) + m1.getDoubleAt(7)  * m2.getDoubleAt(29);
					temp[6] = m1.getDoubleAt(4)   * m2.getDoubleAt(18) + m1.getDoubleAt(5)  * m2.getDoubleAt(22) + m1.getDoubleAt(6)  * m2.getDoubleAt(26) + m1.getDoubleAt(7)  * m2.getDoubleAt(30);
					temp[7] = m1.getDoubleAt(4)   * m2.getDoubleAt(19) + m1.getDoubleAt(5)  * m2.getDoubleAt(23) + m1.getDoubleAt(6)  * m2.getDoubleAt(27) + m1.getDoubleAt(7)  * m2.getDoubleAt(31);
					temp[8] = m1.getDoubleAt(8)   * m2.getDoubleAt(16) + m1.getDoubleAt(9)  * m2.getDoubleAt(20) + m1.getDoubleAt(10) * m2.getDoubleAt(24) + m1.getDoubleAt(11) * m2.getDoubleAt(28);
					temp[9] = m1.getDoubleAt(8)   * m2.getDoubleAt(17) + m1.getDoubleAt(9)  * m2.getDoubleAt(21) + m1.getDoubleAt(10) * m2.getDoubleAt(25) + m1.getDoubleAt(11) * m2.getDoubleAt(29);
					temp[10] = m1.getDoubleAt(8)  * m2.getDoubleAt(18) + m1.getDoubleAt(9)  * m2.getDoubleAt(22) + m1.getDoubleAt(10) * m2.getDoubleAt(26) + m1.getDoubleAt(11) * m2.getDoubleAt(30);
					temp[11] = m1.getDoubleAt(8)  * m2.getDoubleAt(19) + m1.getDoubleAt(9)  * m2.getDoubleAt(23) + m1.getDoubleAt(10) * m2.getDoubleAt(27) + m1.getDoubleAt(11) * m2.getDoubleAt(31);
					temp[12] = m1.getDoubleAt(12) * m2.getDoubleAt(16) + m1.getDoubleAt(13) * m2.getDoubleAt(20) + m1.getDoubleAt(14) * m2.getDoubleAt(24) + m1.getDoubleAt(15) * m2.getDoubleAt(28);
					temp[13] = m1.getDoubleAt(12) * m2.getDoubleAt(17) + m1.getDoubleAt(13) * m2.getDoubleAt(21) + m1.getDoubleAt(14) * m2.getDoubleAt(25) + m1.getDoubleAt(15) * m2.getDoubleAt(29);
					temp[14] = m1.getDoubleAt(12) * m2.getDoubleAt(18) + m1.getDoubleAt(13) * m2.getDoubleAt(22) + m1.getDoubleAt(14) * m2.getDoubleAt(26) + m1.getDoubleAt(15) * m2.getDoubleAt(30);
					temp[15] = m1.getDoubleAt(12) * m2.getDoubleAt(19) + m1.getDoubleAt(13) * m2.getDoubleAt(23) + m1.getDoubleAt(14) * m2.getDoubleAt(27) + m1.getDoubleAt(15) * m2.getDoubleAt(31);
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, m1[0]   * m2[0] + m1[1]  * m2[4] + m1[2]  * m2[8]  + m1[3]  * m2[12]);
				temp.setVectorAt(1, m1[0]   * m2[1] + m1[1]  * m2[5] + m1[2]  * m2[9]  + m1[3]  * m2[13]);
				temp.setVectorAt(2, m1[0]   * m2[2] + m1[1]  * m2[6] + m1[2]  * m2[10] + m1[3]  * m2[14]);
				temp.setVectorAt(3, m1[0]   * m2[3] + m1[1]  * m2[7] + m1[2]  * m2[11] + m1[3]  * m2[15]);
				temp.setVectorAt(4, m1[4]   * m2[0] + m1[5]  * m2[4] + m1[6]  * m2[8]  + m1[7]  * m2[12]);
				temp.setVectorAt(5, m1[4]   * m2[1] + m1[5]  * m2[5] + m1[6]  * m2[9]  + m1[7]  * m2[13]);
				temp.setVectorAt(6, m1[4]   * m2[2] + m1[5]  * m2[6] + m1[6]  * m2[10] + m1[7]  * m2[14]);
				temp.setVectorAt(7, m1[4]   * m2[3] + m1[5]  * m2[7] + m1[6]  * m2[11] + m1[7]  * m2[15]);
				temp.setVectorAt(8, m1[8]   * m2[0] + m1[9]  * m2[4] + m1[10] * m2[8]  + m1[11] * m2[12]);
				temp.setVectorAt(9, m1[8]   * m2[1] + m1[9]  * m2[5] + m1[10] * m2[9]  + m1[11] * m2[13]);
				temp.setVectorAt(10, m1[8]  * m2[2] + m1[9]  * m2[6] + m1[10] * m2[10] + m1[11] * m2[14]);
				temp.setVectorAt(11, m1[8]  * m2[3] + m1[9]  * m2[7] + m1[10] * m2[11] + m1[11] * m2[15]);
				temp.setVectorAt(12, m1[12] * m2[0] + m1[13] * m2[4] + m1[14] * m2[8]  + m1[15] * m2[12]);
				temp.setVectorAt(13, m1[12] * m2[1] + m1[13] * m2[5] + m1[14] * m2[9]  + m1[15] * m2[13]);
				temp.setVectorAt(14, m1[12] * m2[2] + m1[13] * m2[6] + m1[14] * m2[10] + m1[15] * m2[14]);
				temp.setVectorAt(15, m1[12] * m2[3] + m1[13] * m2[7] + m1[14] * m2[11] + m1[15] * m2[15]);
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, m1[0]   * m2.getDoubleAt(0) + m1[1] * m2.getDoubleAt(4)  + m1[2]  * m2.getDoubleAt(8)  + m1[3]  * m2.getDoubleAt(12));
					temp.setVectorAt(1, m1[0]   * m2.getDoubleAt(1) + m1[1] * m2.getDoubleAt(5)  + m1[2]  * m2.getDoubleAt(9)  + m1[3]  * m2.getDoubleAt(13));
					temp.setVectorAt(2, m1[0]   * m2.getDoubleAt(2) + m1[1] * m2.getDoubleAt(6)  + m1[2]  * m2.getDoubleAt(10) + m1[3]  * m2.getDoubleAt(14));
					temp.setVectorAt(3, m1[0]   * m2.getDoubleAt(3) + m1[1] * m2.getDoubleAt(7)  + m1[2]  * m2.getDoubleAt(11) + m1[3]  * m2.getDoubleAt(15));
					temp.setVectorAt(4, m1[4]   * m2.getDoubleAt(0) + m1[5] * m2.getDoubleAt(4)  + m1[6]  * m2.getDoubleAt(8)  + m1[7]  * m2.getDoubleAt(12));
					temp.setVectorAt(5, m1[4]   * m2.getDoubleAt(1) + m1[5] * m2.getDoubleAt(5)  + m1[6]  * m2.getDoubleAt(9)  + m1[7]  * m2.getDoubleAt(13));
					temp.setVectorAt(6, m1[4]   * m2.getDoubleAt(2) + m1[5] * m2.getDoubleAt(6)  + m1[6]  * m2.getDoubleAt(10) + m1[7]  * m2.getDoubleAt(14));
					temp.setVectorAt(7, m1[4]   * m2.getDoubleAt(3) + m1[5] * m2.getDoubleAt(7)  + m1[6]  * m2.getDoubleAt(11) + m1[7]  * m2.getDoubleAt(15));
					temp.setVectorAt(8, m1[8]   * m2.getDoubleAt(0) + m1[9] * m2.getDoubleAt(4)  + m1[10] * m2.getDoubleAt(8)  + m1[11] * m2.getDoubleAt(12));
					temp.setVectorAt(9, m1[8]   * m2.getDoubleAt(1) + m1[9] * m2.getDoubleAt(5)  + m1[10] * m2.getDoubleAt(9)  + m1[11] * m2.getDoubleAt(13));
					temp.setVectorAt(10, m1[8]  * m2.getDoubleAt(2) + m1[9] * m2.getDoubleAt(6)  + m1[10] * m2.getDoubleAt(10) + m1[11] * m2.getDoubleAt(14));
					temp.setVectorAt(11, m1[8]  * m2.getDoubleAt(3) + m1[9] * m2.getDoubleAt(7)  + m1[10] * m2.getDoubleAt(11) + m1[11] * m2.getDoubleAt(15));
					temp.setVectorAt(12, m1[12] * m2.getDoubleAt(0) + m1[13] * m2.getDoubleAt(4) + m1[14] * m2.getDoubleAt(8)  + m1[15] * m2.getDoubleAt(12));
					temp.setVectorAt(13, m1[12] * m2.getDoubleAt(1) + m1[13] * m2.getDoubleAt(5) + m1[14] * m2.getDoubleAt(9)  + m1[15] * m2.getDoubleAt(13));
					temp.setVectorAt(14, m1[12] * m2.getDoubleAt(2) + m1[13] * m2.getDoubleAt(6) + m1[14] * m2.getDoubleAt(10) + m1[15] * m2.getDoubleAt(14));
					temp.setVectorAt(15, m1[12] * m2.getDoubleAt(3) + m1[13] * m2.getDoubleAt(7) + m1[14] * m2.getDoubleAt(11) + m1[15] * m2.getDoubleAt(15));
				} else {
					temp.setVectorAt(16, m1[0]  * m2.getDoubleAt(0) + m1[1]  * m2.getDoubleAt(4) + m1[2]  * m2.getDoubleAt(8)  + m1[3]  * m2.getDoubleAt(12));
					temp.setVectorAt(17, m1[0]  * m2.getDoubleAt(1) + m1[1]  * m2.getDoubleAt(5) + m1[2]  * m2.getDoubleAt(9)  + m1[3]  * m2.getDoubleAt(13));
					temp.setVectorAt(18, m1[0]  * m2.getDoubleAt(2) + m1[1]  * m2.getDoubleAt(6) + m1[2]  * m2.getDoubleAt(10) + m1[3]  * m2.getDoubleAt(14));
					temp.setVectorAt(19, m1[0]  * m2.getDoubleAt(3) + m1[1]  * m2.getDoubleAt(7) + m1[2]  * m2.getDoubleAt(11) + m1[3]  * m2.getDoubleAt(15));
					temp.setVectorAt(20, m1[4]  * m2.getDoubleAt(0) + m1[5]  * m2.getDoubleAt(4) + m1[6]  * m2.getDoubleAt(8)  + m1[7]  * m2.getDoubleAt(12));
					temp.setVectorAt(21, m1[4]  * m2.getDoubleAt(1) + m1[5]  * m2.getDoubleAt(5) + m1[6]  * m2.getDoubleAt(9)  + m1[7]  * m2.getDoubleAt(13));
					temp.setVectorAt(22, m1[4]  * m2.getDoubleAt(2) + m1[5]  * m2.getDoubleAt(6) + m1[6]  * m2.getDoubleAt(10) + m1[7]  * m2.getDoubleAt(14));
					temp.setVectorAt(23, m1[4]  * m2.getDoubleAt(3) + m1[5]  * m2.getDoubleAt(7) + m1[6]  * m2.getDoubleAt(11) + m1[7]  * m2.getDoubleAt(15));
					temp.setVectorAt(24, m1[8]  * m2.getDoubleAt(0) + m1[9]  * m2.getDoubleAt(4) + m1[10] * m2.getDoubleAt(8)  + m1[11] * m2.getDoubleAt(12));
					temp.setVectorAt(25, m1[8]  * m2.getDoubleAt(1) + m1[9]  * m2.getDoubleAt(5) + m1[10] * m2.getDoubleAt(9)  + m1[11] * m2.getDoubleAt(13));
					temp.setVectorAt(26, m1[8]  * m2.getDoubleAt(2) + m1[9]  * m2.getDoubleAt(6) + m1[10] * m2.getDoubleAt(10) + m1[11] * m2.getDoubleAt(14));
					temp.setVectorAt(27, m1[8]  * m2.getDoubleAt(3) + m1[9]  * m2.getDoubleAt(7) + m1[10] * m2.getDoubleAt(11) + m1[11] * m2.getDoubleAt(15));
					temp.setVectorAt(28, m1[12] * m2.getDoubleAt(0) + m1[13] * m2.getDoubleAt(4) + m1[14] * m2.getDoubleAt(8)  + m1[15] * m2.getDoubleAt(12));
					temp.setVectorAt(29, m1[12] * m2.getDoubleAt(1) + m1[13] * m2.getDoubleAt(5) + m1[14] * m2.getDoubleAt(9)  + m1[15] * m2.getDoubleAt(13));
					temp.setVectorAt(30, m1[12] * m2.getDoubleAt(2) + m1[13] * m2.getDoubleAt(6) + m1[14] * m2.getDoubleAt(10) + m1[15] * m2.getDoubleAt(14));
					temp.setVectorAt(31, m1[12] * m2.getDoubleAt(3) + m1[13] * m2.getDoubleAt(7) + m1[14] * m2.getDoubleAt(11) + m1[15] * m2.getDoubleAt(15));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, m1.getDoubleAt(0)   * m2[0] + m1.getDoubleAt(1)  * m2[4] + m1.getDoubleAt(2)  * m2[8]  + m1.getDoubleAt(3)  * m2[12]);
					temp.setVectorAt(1, m1.getDoubleAt(0)   * m2[1] + m1.getDoubleAt(1)  * m2[5] + m1.getDoubleAt(2)  * m2[9]  + m1.getDoubleAt(3)  * m2[13]);
					temp.setVectorAt(2, m1.getDoubleAt(0)   * m2[2] + m1.getDoubleAt(1)  * m2[6] + m1.getDoubleAt(2)  * m2[10] + m1.getDoubleAt(3)  * m2[14]);
					temp.setVectorAt(3, m1.getDoubleAt(0)   * m2[3] + m1.getDoubleAt(1)  * m2[7] + m1.getDoubleAt(2)  * m2[11] + m1.getDoubleAt(3)  * m2[15]);
					temp.setVectorAt(4, m1.getDoubleAt(4)   * m2[0] + m1.getDoubleAt(5)  * m2[4] + m1.getDoubleAt(6)  * m2[8]  + m1.getDoubleAt(7)  * m2[12]);
					temp.setVectorAt(5, m1.getDoubleAt(4)   * m2[1] + m1.getDoubleAt(5)  * m2[5] + m1.getDoubleAt(6)  * m2[9]  + m1.getDoubleAt(7)  * m2[13]);
					temp.setVectorAt(6, m1.getDoubleAt(4)   * m2[2] + m1.getDoubleAt(5)  * m2[6] + m1.getDoubleAt(6)  * m2[10] + m1.getDoubleAt(7)  * m2[14]);
					temp.setVectorAt(7, m1.getDoubleAt(4)   * m2[3] + m1.getDoubleAt(5)  * m2[7] + m1.getDoubleAt(6)  * m2[11] + m1.getDoubleAt(7)  * m2[15]);
					temp.setVectorAt(8, m1.getDoubleAt(8)   * m2[0] + m1.getDoubleAt(9)  * m2[4] + m1.getDoubleAt(10) * m2[8]  + m1.getDoubleAt(11) * m2[12]);
					temp.setVectorAt(9, m1.getDoubleAt(8)   * m2[1] + m1.getDoubleAt(9)  * m2[5] + m1.getDoubleAt(10) * m2[9]  + m1.getDoubleAt(11) * m2[13]);
					temp.setVectorAt(10, m1.getDoubleAt(8)  * m2[2] + m1.getDoubleAt(9)  * m2[6] + m1.getDoubleAt(10) * m2[10] + m1.getDoubleAt(11) * m2[14]);
					temp.setVectorAt(11, m1.getDoubleAt(8)  * m2[3] + m1.getDoubleAt(9)  * m2[7] + m1.getDoubleAt(10) * m2[11] + m1.getDoubleAt(11) * m2[15]);
					temp.setVectorAt(12, m1.getDoubleAt(12) * m2[0] + m1.getDoubleAt(13) * m2[4] + m1.getDoubleAt(14) * m2[8]  + m1.getDoubleAt(15) * m2[12]);
					temp.setVectorAt(13, m1.getDoubleAt(12) * m2[1] + m1.getDoubleAt(13) * m2[5] + m1.getDoubleAt(14) * m2[9]  + m1.getDoubleAt(15) * m2[13]);
					temp.setVectorAt(14, m1.getDoubleAt(12) * m2[2] + m1.getDoubleAt(13) * m2[6] + m1.getDoubleAt(14) * m2[10] + m1.getDoubleAt(15) * m2[14]);
					temp.setVectorAt(15, m1.getDoubleAt(12) * m2[3] + m1.getDoubleAt(13) * m2[7] + m1.getDoubleAt(14) * m2[11] + m1.getDoubleAt(15) * m2[15]);
				} else {
					temp.setVectorAt(16, m1.getDoubleAt(0)  * m2[0] + m1.getDoubleAt(1)  * m2[4] + m1.getDoubleAt(2)  * m2[8]  + m1.getDoubleAt(3)  * m2[12]);
					temp.setVectorAt(17, m1.getDoubleAt(0)  * m2[1] + m1.getDoubleAt(1)  * m2[5] + m1.getDoubleAt(2)  * m2[9]  + m1.getDoubleAt(3)  * m2[13]);
					temp.setVectorAt(18, m1.getDoubleAt(0)  * m2[2] + m1.getDoubleAt(1)  * m2[6] + m1.getDoubleAt(2)  * m2[10] + m1.getDoubleAt(3)  * m2[14]);
					temp.setVectorAt(19, m1.getDoubleAt(0)  * m2[3] + m1.getDoubleAt(1)  * m2[7] + m1.getDoubleAt(2)  * m2[11] + m1.getDoubleAt(3)  * m2[15]);
					temp.setVectorAt(20, m1.getDoubleAt(4)  * m2[0] + m1.getDoubleAt(5)  * m2[4] + m1.getDoubleAt(6)  * m2[8]  + m1.getDoubleAt(7)  * m2[12]);
					temp.setVectorAt(21, m1.getDoubleAt(4)  * m2[1] + m1.getDoubleAt(5)  * m2[5] + m1.getDoubleAt(6)  * m2[9]  + m1.getDoubleAt(7)  * m2[13]);
					temp.setVectorAt(22, m1.getDoubleAt(4)  * m2[2] + m1.getDoubleAt(5)  * m2[6] + m1.getDoubleAt(6)  * m2[10] + m1.getDoubleAt(7)  * m2[14]);
					temp.setVectorAt(23, m1.getDoubleAt(4)  * m2[3] + m1.getDoubleAt(5)  * m2[7] + m1.getDoubleAt(6)  * m2[11] + m1.getDoubleAt(7)  * m2[15]);
					temp.setVectorAt(24, m1.getDoubleAt(8)  * m2[0] + m1.getDoubleAt(9)  * m2[4] + m1.getDoubleAt(10) * m2[8]  + m1.getDoubleAt(11) * m2[12]);
					temp.setVectorAt(25, m1.getDoubleAt(8)  * m2[1] + m1.getDoubleAt(9)  * m2[5] + m1.getDoubleAt(10) * m2[9]  + m1.getDoubleAt(11) * m2[13]);
					temp.setVectorAt(26, m1.getDoubleAt(8)  * m2[2] + m1.getDoubleAt(9)  * m2[6] + m1.getDoubleAt(10) * m2[10] + m1.getDoubleAt(11) * m2[14]);
					temp.setVectorAt(27, m1.getDoubleAt(8)  * m2[3] + m1.getDoubleAt(9)  * m2[7] + m1.getDoubleAt(10) * m2[11] + m1.getDoubleAt(11) * m2[15]);
					temp.setVectorAt(28, m1.getDoubleAt(12) * m2[0] + m1.getDoubleAt(13) * m2[4] + m1.getDoubleAt(14) * m2[8]  + m1.getDoubleAt(15) * m2[12]);
					temp.setVectorAt(29, m1.getDoubleAt(12) * m2[1] + m1.getDoubleAt(13) * m2[5] + m1.getDoubleAt(14) * m2[9]  + m1.getDoubleAt(15) * m2[13]);
					temp.setVectorAt(30, m1.getDoubleAt(12) * m2[2] + m1.getDoubleAt(13) * m2[6] + m1.getDoubleAt(14) * m2[10] + m1.getDoubleAt(15) * m2[14]);
					temp.setVectorAt(31, m1.getDoubleAt(12) * m2[3] + m1.getDoubleAt(13) * m2[7] + m1.getDoubleAt(14) * m2[11] + m1.getDoubleAt(15) * m2[15]);
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, m1.getDoubleAt(0)   * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(4) + m1.getDoubleAt(2)  * m2.getDoubleAt(8)  + m1.getDoubleAt(3)  * m2.getDoubleAt(12));
						temp.setVectorAt(1, m1.getDoubleAt(0)   * m2.getDoubleAt(1) + m1.getDoubleAt(1)  * m2.getDoubleAt(5) + m1.getDoubleAt(2)  * m2.getDoubleAt(9)  + m1.getDoubleAt(3)  * m2.getDoubleAt(13));
						temp.setVectorAt(2, m1.getDoubleAt(0)   * m2.getDoubleAt(2) + m1.getDoubleAt(1)  * m2.getDoubleAt(6) + m1.getDoubleAt(2)  * m2.getDoubleAt(10) + m1.getDoubleAt(3)  * m2.getDoubleAt(14));
						temp.setVectorAt(3, m1.getDoubleAt(0)   * m2.getDoubleAt(3) + m1.getDoubleAt(1)  * m2.getDoubleAt(7) + m1.getDoubleAt(2)  * m2.getDoubleAt(11) + m1.getDoubleAt(3)  * m2.getDoubleAt(15));
						temp.setVectorAt(4, m1.getDoubleAt(4)   * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(4) + m1.getDoubleAt(6)  * m2.getDoubleAt(8)  + m1.getDoubleAt(7)  * m2.getDoubleAt(12));
						temp.setVectorAt(5, m1.getDoubleAt(4)   * m2.getDoubleAt(1) + m1.getDoubleAt(5)  * m2.getDoubleAt(5) + m1.getDoubleAt(6)  * m2.getDoubleAt(9)  + m1.getDoubleAt(7)  * m2.getDoubleAt(13));
						temp.setVectorAt(6, m1.getDoubleAt(4)   * m2.getDoubleAt(2) + m1.getDoubleAt(5)  * m2.getDoubleAt(6) + m1.getDoubleAt(6)  * m2.getDoubleAt(10) + m1.getDoubleAt(7)  * m2.getDoubleAt(14));
						temp.setVectorAt(7, m1.getDoubleAt(4)   * m2.getDoubleAt(3) + m1.getDoubleAt(5)  * m2.getDoubleAt(7) + m1.getDoubleAt(6)  * m2.getDoubleAt(11) + m1.getDoubleAt(7)  * m2.getDoubleAt(15));
						temp.setVectorAt(8, m1.getDoubleAt(8)   * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(4) + m1.getDoubleAt(10) * m2.getDoubleAt(8)  + m1.getDoubleAt(11) * m2.getDoubleAt(12));
						temp.setVectorAt(9, m1.getDoubleAt(8)   * m2.getDoubleAt(1) + m1.getDoubleAt(9)  * m2.getDoubleAt(5) + m1.getDoubleAt(10) * m2.getDoubleAt(9)  + m1.getDoubleAt(11) * m2.getDoubleAt(13));
						temp.setVectorAt(10, m1.getDoubleAt(8)  * m2.getDoubleAt(2) + m1.getDoubleAt(9)  * m2.getDoubleAt(6) + m1.getDoubleAt(10) * m2.getDoubleAt(10) + m1.getDoubleAt(11) * m2.getDoubleAt(14));
						temp.setVectorAt(11, m1.getDoubleAt(8)  * m2.getDoubleAt(3) + m1.getDoubleAt(9)  * m2.getDoubleAt(7) + m1.getDoubleAt(10) * m2.getDoubleAt(11) + m1.getDoubleAt(11) * m2.getDoubleAt(15));
						temp.setVectorAt(12, m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(4) + m1.getDoubleAt(14) * m2.getDoubleAt(8)  + m1.getDoubleAt(15) * m2.getDoubleAt(12));
						temp.setVectorAt(13, m1.getDoubleAt(12) * m2.getDoubleAt(1) + m1.getDoubleAt(13) * m2.getDoubleAt(5) + m1.getDoubleAt(14) * m2.getDoubleAt(9)  + m1.getDoubleAt(15) * m2.getDoubleAt(13));
						temp.setVectorAt(14, m1.getDoubleAt(12) * m2.getDoubleAt(2) + m1.getDoubleAt(13) * m2.getDoubleAt(6) + m1.getDoubleAt(14) * m2.getDoubleAt(10) + m1.getDoubleAt(15) * m2.getDoubleAt(14));
						temp.setVectorAt(15, m1.getDoubleAt(12) * m2.getDoubleAt(3) + m1.getDoubleAt(13) * m2.getDoubleAt(7) + m1.getDoubleAt(14) * m2.getDoubleAt(11) + m1.getDoubleAt(15) * m2.getDoubleAt(15));
					} else {
						temp.setVectorAt(0, m1.getDoubleAt(0)   * m2.getDoubleAt(16) + m1.getDoubleAt(1)  * m2.getDoubleAt(20) + m1.getDoubleAt(2)  * m2.getDoubleAt(24) + m1.getDoubleAt(3)  * m2.getDoubleAt(28));
						temp.setVectorAt(1, m1.getDoubleAt(0)   * m2.getDoubleAt(17) + m1.getDoubleAt(1)  * m2.getDoubleAt(21) + m1.getDoubleAt(2)  * m2.getDoubleAt(25) + m1.getDoubleAt(3)  * m2.getDoubleAt(29));
						temp.setVectorAt(2, m1.getDoubleAt(0)   * m2.getDoubleAt(18) + m1.getDoubleAt(1)  * m2.getDoubleAt(22) + m1.getDoubleAt(2)  * m2.getDoubleAt(26) + m1.getDoubleAt(3)  * m2.getDoubleAt(30));
						temp.setVectorAt(3, m1.getDoubleAt(0)   * m2.getDoubleAt(19) + m1.getDoubleAt(1)  * m2.getDoubleAt(23) + m1.getDoubleAt(2)  * m2.getDoubleAt(27) + m1.getDoubleAt(3)  * m2.getDoubleAt(31));
						temp.setVectorAt(4, m1.getDoubleAt(4)   * m2.getDoubleAt(16) + m1.getDoubleAt(5)  * m2.getDoubleAt(20) + m1.getDoubleAt(6)  * m2.getDoubleAt(24) + m1.getDoubleAt(7)  * m2.getDoubleAt(28));
						temp.setVectorAt(5, m1.getDoubleAt(4)   * m2.getDoubleAt(17) + m1.getDoubleAt(5)  * m2.getDoubleAt(21) + m1.getDoubleAt(6)  * m2.getDoubleAt(25) + m1.getDoubleAt(7)  * m2.getDoubleAt(29));
						temp.setVectorAt(6, m1.getDoubleAt(4)   * m2.getDoubleAt(18) + m1.getDoubleAt(5)  * m2.getDoubleAt(22) + m1.getDoubleAt(6)  * m2.getDoubleAt(26) + m1.getDoubleAt(7)  * m2.getDoubleAt(30));
						temp.setVectorAt(7, m1.getDoubleAt(4)   * m2.getDoubleAt(19) + m1.getDoubleAt(5)  * m2.getDoubleAt(23) + m1.getDoubleAt(6)  * m2.getDoubleAt(27) + m1.getDoubleAt(7)  * m2.getDoubleAt(31));
						temp.setVectorAt(8, m1.getDoubleAt(8)   * m2.getDoubleAt(16) + m1.getDoubleAt(9)  * m2.getDoubleAt(20) + m1.getDoubleAt(10) * m2.getDoubleAt(24) + m1.getDoubleAt(11) * m2.getDoubleAt(28));
						temp.setVectorAt(9, m1.getDoubleAt(8)   * m2.getDoubleAt(17) + m1.getDoubleAt(9)  * m2.getDoubleAt(21) + m1.getDoubleAt(10) * m2.getDoubleAt(25) + m1.getDoubleAt(11) * m2.getDoubleAt(29));
						temp.setVectorAt(10, m1.getDoubleAt(8)  * m2.getDoubleAt(18) + m1.getDoubleAt(9)  * m2.getDoubleAt(22) + m1.getDoubleAt(10) * m2.getDoubleAt(26) + m1.getDoubleAt(11) * m2.getDoubleAt(30));
						temp.setVectorAt(11, m1.getDoubleAt(8)  * m2.getDoubleAt(19) + m1.getDoubleAt(9)  * m2.getDoubleAt(23) + m1.getDoubleAt(10) * m2.getDoubleAt(27) + m1.getDoubleAt(11) * m2.getDoubleAt(31));
						temp.setVectorAt(12, m1.getDoubleAt(12) * m2.getDoubleAt(16) + m1.getDoubleAt(13) * m2.getDoubleAt(20) + m1.getDoubleAt(14) * m2.getDoubleAt(24) + m1.getDoubleAt(15) * m2.getDoubleAt(28));
						temp.setVectorAt(13, m1.getDoubleAt(12) * m2.getDoubleAt(17) + m1.getDoubleAt(13) * m2.getDoubleAt(21) + m1.getDoubleAt(14) * m2.getDoubleAt(25) + m1.getDoubleAt(15) * m2.getDoubleAt(29));
						temp.setVectorAt(14, m1.getDoubleAt(12) * m2.getDoubleAt(18) + m1.getDoubleAt(13) * m2.getDoubleAt(22) + m1.getDoubleAt(14) * m2.getDoubleAt(26) + m1.getDoubleAt(15) * m2.getDoubleAt(30));
						temp.setVectorAt(15, m1.getDoubleAt(12) * m2.getDoubleAt(19) + m1.getDoubleAt(13) * m2.getDoubleAt(23) + m1.getDoubleAt(14) * m2.getDoubleAt(27) + m1.getDoubleAt(15) * m2.getDoubleAt(31));
					}
				} else if(m2 != temp || m1 != temp) {
					temp.setVectorAt(16, m1.getDoubleAt(0)  * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(4) + m1.getDoubleAt(2)  * m2.getDoubleAt(8)  + m1.getDoubleAt(3)  * m2.getDoubleAt(12));
					temp.setVectorAt(17, m1.getDoubleAt(0)  * m2.getDoubleAt(1) + m1.getDoubleAt(1)  * m2.getDoubleAt(5) + m1.getDoubleAt(2)  * m2.getDoubleAt(9)  + m1.getDoubleAt(3)  * m2.getDoubleAt(13));
					temp.setVectorAt(18, m1.getDoubleAt(0)  * m2.getDoubleAt(2) + m1.getDoubleAt(1)  * m2.getDoubleAt(6) + m1.getDoubleAt(2)  * m2.getDoubleAt(10) + m1.getDoubleAt(3)  * m2.getDoubleAt(14));
					temp.setVectorAt(19, m1.getDoubleAt(0)  * m2.getDoubleAt(3) + m1.getDoubleAt(1)  * m2.getDoubleAt(7) + m1.getDoubleAt(2)  * m2.getDoubleAt(11) + m1.getDoubleAt(3)  * m2.getDoubleAt(15));
					temp.setVectorAt(20, m1.getDoubleAt(4)  * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(4) + m1.getDoubleAt(6)  * m2.getDoubleAt(8)  + m1.getDoubleAt(7)  * m2.getDoubleAt(12));
					temp.setVectorAt(21, m1.getDoubleAt(4)  * m2.getDoubleAt(1) + m1.getDoubleAt(5)  * m2.getDoubleAt(5) + m1.getDoubleAt(6)  * m2.getDoubleAt(9)  + m1.getDoubleAt(7)  * m2.getDoubleAt(13));
					temp.setVectorAt(22, m1.getDoubleAt(4)  * m2.getDoubleAt(2) + m1.getDoubleAt(5)  * m2.getDoubleAt(6) + m1.getDoubleAt(6)  * m2.getDoubleAt(10) + m1.getDoubleAt(7)  * m2.getDoubleAt(14));
					temp.setVectorAt(23, m1.getDoubleAt(4)  * m2.getDoubleAt(3) + m1.getDoubleAt(5)  * m2.getDoubleAt(7) + m1.getDoubleAt(6)  * m2.getDoubleAt(11) + m1.getDoubleAt(7)  * m2.getDoubleAt(15));
					temp.setVectorAt(24, m1.getDoubleAt(8)  * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(4) + m1.getDoubleAt(10) * m2.getDoubleAt(8)  + m1.getDoubleAt(11) * m2.getDoubleAt(12));
					temp.setVectorAt(25, m1.getDoubleAt(8)  * m2.getDoubleAt(1) + m1.getDoubleAt(9)  * m2.getDoubleAt(5) + m1.getDoubleAt(10) * m2.getDoubleAt(9)  + m1.getDoubleAt(11) * m2.getDoubleAt(13));
					temp.setVectorAt(26, m1.getDoubleAt(8)  * m2.getDoubleAt(2) + m1.getDoubleAt(9)  * m2.getDoubleAt(6) + m1.getDoubleAt(10) * m2.getDoubleAt(10) + m1.getDoubleAt(11) * m2.getDoubleAt(14));
					temp.setVectorAt(27, m1.getDoubleAt(8)  * m2.getDoubleAt(3) + m1.getDoubleAt(9)  * m2.getDoubleAt(7) + m1.getDoubleAt(10) * m2.getDoubleAt(11) + m1.getDoubleAt(11) * m2.getDoubleAt(15));
					temp.setVectorAt(28, m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(4) + m1.getDoubleAt(14) * m2.getDoubleAt(8)  + m1.getDoubleAt(15) * m2.getDoubleAt(12));
					temp.setVectorAt(29, m1.getDoubleAt(12) * m2.getDoubleAt(1) + m1.getDoubleAt(13) * m2.getDoubleAt(5) + m1.getDoubleAt(14) * m2.getDoubleAt(9)  + m1.getDoubleAt(15) * m2.getDoubleAt(13));
					temp.setVectorAt(30, m1.getDoubleAt(12) * m2.getDoubleAt(2) + m1.getDoubleAt(13) * m2.getDoubleAt(6) + m1.getDoubleAt(14) * m2.getDoubleAt(10) + m1.getDoubleAt(15) * m2.getDoubleAt(14));
					temp.setVectorAt(31, m1.getDoubleAt(12) * m2.getDoubleAt(3) + m1.getDoubleAt(13) * m2.getDoubleAt(7) + m1.getDoubleAt(14) * m2.getDoubleAt(11) + m1.getDoubleAt(15) * m2.getDoubleAt(15));
				} else {
					temp.setVectorAt(32, m1.getDoubleAt(0)  * m2.getDoubleAt(16) + m1.getDoubleAt(1)  * m2.getDoubleAt(20) + m1.getDoubleAt(2)  * m2.getDoubleAt(24) + m1.getDoubleAt(3)  * m2.getDoubleAt(28));
					temp.setVectorAt(33, m1.getDoubleAt(0)  * m2.getDoubleAt(17) + m1.getDoubleAt(1)  * m2.getDoubleAt(21) + m1.getDoubleAt(2)  * m2.getDoubleAt(25) + m1.getDoubleAt(3)  * m2.getDoubleAt(29));
					temp.setVectorAt(34, m1.getDoubleAt(0)  * m2.getDoubleAt(18) + m1.getDoubleAt(1)  * m2.getDoubleAt(22) + m1.getDoubleAt(2)  * m2.getDoubleAt(26) + m1.getDoubleAt(3)  * m2.getDoubleAt(30));
					temp.setVectorAt(35, m1.getDoubleAt(0)  * m2.getDoubleAt(19) + m1.getDoubleAt(1)  * m2.getDoubleAt(23) + m1.getDoubleAt(2)  * m2.getDoubleAt(27) + m1.getDoubleAt(3)  * m2.getDoubleAt(31));
					temp.setVectorAt(36, m1.getDoubleAt(4)  * m2.getDoubleAt(16) + m1.getDoubleAt(5)  * m2.getDoubleAt(20) + m1.getDoubleAt(6)  * m2.getDoubleAt(24) + m1.getDoubleAt(7)  * m2.getDoubleAt(28));
					temp.setVectorAt(37, m1.getDoubleAt(4)  * m2.getDoubleAt(17) + m1.getDoubleAt(5)  * m2.getDoubleAt(21) + m1.getDoubleAt(6)  * m2.getDoubleAt(25) + m1.getDoubleAt(7)  * m2.getDoubleAt(29));
					temp.setVectorAt(38, m1.getDoubleAt(4)  * m2.getDoubleAt(18) + m1.getDoubleAt(5)  * m2.getDoubleAt(22) + m1.getDoubleAt(6)  * m2.getDoubleAt(26) + m1.getDoubleAt(7)  * m2.getDoubleAt(30));
					temp.setVectorAt(39, m1.getDoubleAt(4)  * m2.getDoubleAt(19) + m1.getDoubleAt(5)  * m2.getDoubleAt(23) + m1.getDoubleAt(6)  * m2.getDoubleAt(27) + m1.getDoubleAt(7)  * m2.getDoubleAt(31));
					temp.setVectorAt(40, m1.getDoubleAt(8)  * m2.getDoubleAt(16) + m1.getDoubleAt(9)  * m2.getDoubleAt(20) + m1.getDoubleAt(10) * m2.getDoubleAt(24) + m1.getDoubleAt(11) * m2.getDoubleAt(28));
					temp.setVectorAt(41, m1.getDoubleAt(8)  * m2.getDoubleAt(17) + m1.getDoubleAt(9)  * m2.getDoubleAt(21) + m1.getDoubleAt(10) * m2.getDoubleAt(25) + m1.getDoubleAt(11) * m2.getDoubleAt(29));
					temp.setVectorAt(42, m1.getDoubleAt(8)  * m2.getDoubleAt(18) + m1.getDoubleAt(9)  * m2.getDoubleAt(22) + m1.getDoubleAt(10) * m2.getDoubleAt(26) + m1.getDoubleAt(11) * m2.getDoubleAt(30));
					temp.setVectorAt(43, m1.getDoubleAt(8)  * m2.getDoubleAt(19) + m1.getDoubleAt(9)  * m2.getDoubleAt(23) + m1.getDoubleAt(10) * m2.getDoubleAt(27) + m1.getDoubleAt(11) * m2.getDoubleAt(31));
					temp.setVectorAt(44, m1.getDoubleAt(12) * m2.getDoubleAt(16) + m1.getDoubleAt(13) * m2.getDoubleAt(20) + m1.getDoubleAt(14) * m2.getDoubleAt(24) + m1.getDoubleAt(15) * m2.getDoubleAt(28));
					temp.setVectorAt(45, m1.getDoubleAt(12) * m2.getDoubleAt(17) + m1.getDoubleAt(13) * m2.getDoubleAt(21) + m1.getDoubleAt(14) * m2.getDoubleAt(25) + m1.getDoubleAt(15) * m2.getDoubleAt(29));
					temp.setVectorAt(46, m1.getDoubleAt(12) * m2.getDoubleAt(18) + m1.getDoubleAt(13) * m2.getDoubleAt(22) + m1.getDoubleAt(14) * m2.getDoubleAt(26) + m1.getDoubleAt(15) * m2.getDoubleAt(30));
					temp.setVectorAt(47, m1.getDoubleAt(12) * m2.getDoubleAt(19) + m1.getDoubleAt(13) * m2.getDoubleAt(23) + m1.getDoubleAt(14) * m2.getDoubleAt(27) + m1.getDoubleAt(15) * m2.getDoubleAt(31));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_4x4_4x1(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 4; final int nM1 = 4; final int mM2 = 4; final int nM2 = 1;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1[0]  * m2[0] + m1[1]  * m2[1] + m1[2]  * m2[2] + m1[3]  * m2[3];
				temp[1] = m1[4]  * m2[0] + m1[5]  * m2[1] + m1[6]  * m2[2] + m1[7]  * m2[3];
				temp[2] = m1[8]  * m2[0] + m1[9]  * m2[1] + m1[10] * m2[2] + m1[11] * m2[3];
				temp[3] = m1[12] * m2[0] + m1[13] * m2[1] + m1[14] * m2[2] + m1[15] * m2[3];
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = m1[0]  * m2.getDoubleAt(0) + m1[1]  * m2.getDoubleAt(1) + m1[2]  * m2.getDoubleAt(2) + m1[3]  * m2.getDoubleAt(3);
				temp[1] = m1[4]  * m2.getDoubleAt(0) + m1[5]  * m2.getDoubleAt(1) + m1[6]  * m2.getDoubleAt(2) + m1[7]  * m2.getDoubleAt(3);
				temp[2] = m1[8]  * m2.getDoubleAt(0) + m1[9]  * m2.getDoubleAt(1) + m1[10] * m2.getDoubleAt(2) + m1[11] * m2.getDoubleAt(3);
				temp[3] = m1[12] * m2.getDoubleAt(0) + m1[13] * m2.getDoubleAt(1) + m1[14] * m2.getDoubleAt(2) + m1[15] * m2.getDoubleAt(3);
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = m1.getDoubleAt(0)  * m2[0] + m1.getDoubleAt(1)  * m2[1] + m1.getDoubleAt(2)  * m2[2] + m1.getDoubleAt(3)  * m2[3];
				temp[1] = m1.getDoubleAt(4)  * m2[0] + m1.getDoubleAt(5)  * m2[1] + m1.getDoubleAt(6)  * m2[2] + m1.getDoubleAt(7)  * m2[3];
				temp[2] = m1.getDoubleAt(8)  * m2[0] + m1.getDoubleAt(9)  * m2[1] + m1.getDoubleAt(10) * m2[2] + m1.getDoubleAt(11) * m2[3];
				temp[3] = m1.getDoubleAt(12) * m2[0] + m1.getDoubleAt(13) * m2[1] + m1.getDoubleAt(14) * m2[2] + m1.getDoubleAt(15) * m2[3];
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = m1.getDoubleAt(0)  * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(1) + m1.getDoubleAt(2)  * m2.getDoubleAt(2) + m1.getDoubleAt(3)  * m2.getDoubleAt(3);
					temp[1] = m1.getDoubleAt(4)  * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(1) + m1.getDoubleAt(6)  * m2.getDoubleAt(2) + m1.getDoubleAt(7)  * m2.getDoubleAt(3);
					temp[2] = m1.getDoubleAt(8)  * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(1) + m1.getDoubleAt(10) * m2.getDoubleAt(2) + m1.getDoubleAt(11) * m2.getDoubleAt(3);
					temp[3] = m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(1) + m1.getDoubleAt(14) * m2.getDoubleAt(2) + m1.getDoubleAt(15) * m2.getDoubleAt(3);
				} else {
					temp[0] = m1.getDoubleAt(0)  * m2.getDoubleAt(16) + m1.getDoubleAt(1)  * m2.getDoubleAt(17) + m1.getDoubleAt(2)  * m2.getDoubleAt(18) + m1.getDoubleAt(3)  * m2.getDoubleAt(19);
					temp[1] = m1.getDoubleAt(4)  * m2.getDoubleAt(16) + m1.getDoubleAt(5)  * m2.getDoubleAt(17) + m1.getDoubleAt(6)  * m2.getDoubleAt(18) + m1.getDoubleAt(7)  * m2.getDoubleAt(19);
					temp[2] = m1.getDoubleAt(8)  * m2.getDoubleAt(16) + m1.getDoubleAt(9)  * m2.getDoubleAt(17) + m1.getDoubleAt(10) * m2.getDoubleAt(18) + m1.getDoubleAt(11) * m2.getDoubleAt(19);
					temp[3] = m1.getDoubleAt(12) * m2.getDoubleAt(16) + m1.getDoubleAt(13) * m2.getDoubleAt(17) + m1.getDoubleAt(14) * m2.getDoubleAt(18) + m1.getDoubleAt(15) * m2.getDoubleAt(19);
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, m1[0]  * m2[0] + m1[1]  * m2[1] + m1[2]  * m2[2] + m1[3]  * m2[3]);
				temp.setVectorAt(1, m1[4]  * m2[0] + m1[5]  * m2[1] + m1[6]  * m2[2] + m1[7]  * m2[3]);
				temp.setVectorAt(2, m1[8]  * m2[0] + m1[9]  * m2[1] + m1[10] * m2[2] + m1[11] * m2[3]);
				temp.setVectorAt(3, m1[12] * m2[0] + m1[13] * m2[1] + m1[14] * m2[2] + m1[15] * m2[3]);
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, m1[0]  * m2.getDoubleAt(0) + m1[1]  * m2.getDoubleAt(1) + m1[2]  * m2.getDoubleAt(2) + m1[3]  * m2.getDoubleAt(3));
					temp.setVectorAt(1, m1[4]  * m2.getDoubleAt(0) + m1[5]  * m2.getDoubleAt(1) + m1[6]  * m2.getDoubleAt(2) + m1[7]  * m2.getDoubleAt(3));
					temp.setVectorAt(2, m1[8]  * m2.getDoubleAt(0) + m1[9]  * m2.getDoubleAt(1) + m1[10] * m2.getDoubleAt(2) + m1[11] * m2.getDoubleAt(3));
					temp.setVectorAt(3, m1[12] * m2.getDoubleAt(0) + m1[13] * m2.getDoubleAt(1) + m1[14] * m2.getDoubleAt(2) + m1[15] * m2.getDoubleAt(3));
				} else {
					temp.setVectorAt(4, m1[0]  * m2.getDoubleAt(0) + m1[1]  * m2.getDoubleAt(1) + m1[2]  * m2.getDoubleAt(2) + m1[3]  * m2.getDoubleAt(3));
					temp.setVectorAt(5, m1[4]  * m2.getDoubleAt(0) + m1[5]  * m2.getDoubleAt(1) + m1[6]  * m2.getDoubleAt(2) + m1[7]  * m2.getDoubleAt(3));
					temp.setVectorAt(6, m1[8]  * m2.getDoubleAt(0) + m1[9]  * m2.getDoubleAt(1) + m1[10] * m2.getDoubleAt(2) + m1[11] * m2.getDoubleAt(3));
					temp.setVectorAt(7, m1[12] * m2.getDoubleAt(0) + m1[13] * m2.getDoubleAt(1) + m1[14] * m2.getDoubleAt(2) + m1[15] * m2.getDoubleAt(3));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, m1.getDoubleAt(0)  * m2[0] + m1.getDoubleAt(1)  * m2[1] + m1.getDoubleAt(2)  * m2[2] + m1.getDoubleAt(3)  * m2[3]);
					temp.setVectorAt(1, m1.getDoubleAt(4)  * m2[0] + m1.getDoubleAt(5)  * m2[1] + m1.getDoubleAt(6)  * m2[2] + m1.getDoubleAt(7)  * m2[3]);
					temp.setVectorAt(2, m1.getDoubleAt(8)  * m2[0] + m1.getDoubleAt(9)  * m2[1] + m1.getDoubleAt(10) * m2[2] + m1.getDoubleAt(11) * m2[3]);
					temp.setVectorAt(3, m1.getDoubleAt(12) * m2[0] + m1.getDoubleAt(13) * m2[1] + m1.getDoubleAt(14) * m2[2] + m1.getDoubleAt(15) * m2[3]);
				} else {
					temp.setVectorAt(16, m1.getDoubleAt(0)  * m2[0] + m1.getDoubleAt(1)  * m2[1] + m1.getDoubleAt(2)  * m2[2] + m1.getDoubleAt(3)  * m2[3]);
					temp.setVectorAt(17, m1.getDoubleAt(4)  * m2[0] + m1.getDoubleAt(5)  * m2[1] + m1.getDoubleAt(6)  * m2[2] + m1.getDoubleAt(7)  * m2[3]);
					temp.setVectorAt(18, m1.getDoubleAt(8)  * m2[0] + m1.getDoubleAt(9)  * m2[1] + m1.getDoubleAt(10) * m2[2] + m1.getDoubleAt(11) * m2[3]);
					temp.setVectorAt(19, m1.getDoubleAt(12) * m2[0] + m1.getDoubleAt(13) * m2[1] + m1.getDoubleAt(14) * m2[2] + m1.getDoubleAt(15) * m2[3]);
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, m1.getDoubleAt(0)  * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(1) + m1.getDoubleAt(2)  * m2.getDoubleAt(2) + m1.getDoubleAt(3)  * m2.getDoubleAt(3));
						temp.setVectorAt(1, m1.getDoubleAt(4)  * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(1) + m1.getDoubleAt(6)  * m2.getDoubleAt(2) + m1.getDoubleAt(7)  * m2.getDoubleAt(3));
						temp.setVectorAt(2, m1.getDoubleAt(8)  * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(1) + m1.getDoubleAt(10) * m2.getDoubleAt(2) + m1.getDoubleAt(11) * m2.getDoubleAt(3));
						temp.setVectorAt(3, m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(1) + m1.getDoubleAt(14) * m2.getDoubleAt(2) + m1.getDoubleAt(15) * m2.getDoubleAt(3));
					} else {
						temp.setVectorAt(0, m1.getDoubleAt(0)  * m2.getDoubleAt(16) + m1.getDoubleAt(1)  * m2.getDoubleAt(17) + m1.getDoubleAt(2)  * m2.getDoubleAt(18) + m1.getDoubleAt(3)  * m2.getDoubleAt(19));
						temp.setVectorAt(1, m1.getDoubleAt(4)  * m2.getDoubleAt(16) + m1.getDoubleAt(5)  * m2.getDoubleAt(17) + m1.getDoubleAt(6)  * m2.getDoubleAt(18) + m1.getDoubleAt(7)  * m2.getDoubleAt(19));
						temp.setVectorAt(2, m1.getDoubleAt(8)  * m2.getDoubleAt(16) + m1.getDoubleAt(9)  * m2.getDoubleAt(17) + m1.getDoubleAt(10) * m2.getDoubleAt(18) + m1.getDoubleAt(11) * m2.getDoubleAt(19));
						temp.setVectorAt(3, m1.getDoubleAt(12) * m2.getDoubleAt(16) + m1.getDoubleAt(13) * m2.getDoubleAt(17) + m1.getDoubleAt(14) * m2.getDoubleAt(18) + m1.getDoubleAt(15) * m2.getDoubleAt(19));
					}
				} else if(m1 != temp) {
					temp.setVectorAt(4, m1.getDoubleAt(0)  * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(1) + m1.getDoubleAt(2)  * m2.getDoubleAt(2) + m1.getDoubleAt(3)  * m2.getDoubleAt(3));
					temp.setVectorAt(5, m1.getDoubleAt(4)  * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(1) + m1.getDoubleAt(6)  * m2.getDoubleAt(2) + m1.getDoubleAt(7)  * m2.getDoubleAt(3));
					temp.setVectorAt(6, m1.getDoubleAt(8)  * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(1) + m1.getDoubleAt(10) * m2.getDoubleAt(2) + m1.getDoubleAt(11) * m2.getDoubleAt(3));
					temp.setVectorAt(7, m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(1) + m1.getDoubleAt(14) * m2.getDoubleAt(2) + m1.getDoubleAt(15) * m2.getDoubleAt(3));
				} else if(m2 != temp) {
					temp.setVectorAt(16, m1.getDoubleAt(0)  * m2.getDoubleAt(0) + m1.getDoubleAt(1)  * m2.getDoubleAt(1) + m1.getDoubleAt(2)  * m2.getDoubleAt(2) + m1.getDoubleAt(3)  * m2.getDoubleAt(3));
					temp.setVectorAt(17, m1.getDoubleAt(4)  * m2.getDoubleAt(0) + m1.getDoubleAt(5)  * m2.getDoubleAt(1) + m1.getDoubleAt(6)  * m2.getDoubleAt(2) + m1.getDoubleAt(7)  * m2.getDoubleAt(3));
					temp.setVectorAt(18, m1.getDoubleAt(8)  * m2.getDoubleAt(0) + m1.getDoubleAt(9)  * m2.getDoubleAt(1) + m1.getDoubleAt(10) * m2.getDoubleAt(2) + m1.getDoubleAt(11) * m2.getDoubleAt(3));
					temp.setVectorAt(19, m1.getDoubleAt(12) * m2.getDoubleAt(0) + m1.getDoubleAt(13) * m2.getDoubleAt(1) + m1.getDoubleAt(14) * m2.getDoubleAt(2) + m1.getDoubleAt(15) * m2.getDoubleAt(3));
				} else {
					temp.setVectorAt(20, m1.getDoubleAt(0)  * m2.getDoubleAt(16) + m1.getDoubleAt(1)  * m2.getDoubleAt(17) + m1.getDoubleAt(2)  * m2.getDoubleAt(18) + m1.getDoubleAt(3)  * m2.getDoubleAt(19));
					temp.setVectorAt(21, m1.getDoubleAt(4)  * m2.getDoubleAt(16) + m1.getDoubleAt(5)  * m2.getDoubleAt(17) + m1.getDoubleAt(6)  * m2.getDoubleAt(18) + m1.getDoubleAt(7)  * m2.getDoubleAt(19));
					temp.setVectorAt(22, m1.getDoubleAt(8)  * m2.getDoubleAt(16) + m1.getDoubleAt(9)  * m2.getDoubleAt(17) + m1.getDoubleAt(10) * m2.getDoubleAt(18) + m1.getDoubleAt(11) * m2.getDoubleAt(19));
					temp.setVectorAt(23, m1.getDoubleAt(12) * m2.getDoubleAt(16) + m1.getDoubleAt(13) * m2.getDoubleAt(17) + m1.getDoubleAt(14) * m2.getDoubleAt(18) + m1.getDoubleAt(15) * m2.getDoubleAt(19));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_2x2_2x2_FMA(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 2; final int nM1 = 2; final int mM2 = 2; final int nM2 = 2;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1[0], m2[0], fma(m1[1], m2[2]));
				temp[1] = fma(m1[0], m2[1], fma(m1[1], m2[3]));
				temp[2] = fma(m1[2], m2[0], fma(m1[3], m2[2]));
				temp[3] = fma(m1[2], m2[1], fma(m1[3], m2[3]));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(2)));
				temp[1] = fma(m1[0], m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(3)));
				temp[2] = fma(m1[2], m2.getDoubleAt(0), fma(m1[3], m2.getDoubleAt(2)));
				temp[3] = fma(m1[2], m2.getDoubleAt(1), fma(m1[3], m2.getDoubleAt(3)));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[2]));
				temp[1] = fma(m1.getDoubleAt(0), m2[1], fma(m1.getDoubleAt(1), m2[3]));
				temp[2] = fma(m1.getDoubleAt(2), m2[0], fma(m1.getDoubleAt(3), m2[2]));
				temp[3] = fma(m1.getDoubleAt(2), m2[1], fma(m1.getDoubleAt(3), m2[3]));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(2)));
					temp[1] = fma(m1.getDoubleAt(0), m2.getDoubleAt(1), fma(m1.getDoubleAt(1), m2.getDoubleAt(3)));
					temp[2] = fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(2)));
					temp[3] = fma(m1.getDoubleAt(2), m2.getDoubleAt(1), fma(m1.getDoubleAt(3), m2.getDoubleAt(3)));
				} else {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(4), fma(m1.getDoubleAt(1), m2.getDoubleAt(6)));
					temp[1] = fma(m1.getDoubleAt(0), m2.getDoubleAt(5), fma(m1.getDoubleAt(1), m2.getDoubleAt(7)));
					temp[2] = fma(m1.getDoubleAt(2), m2.getDoubleAt(4), fma(m1.getDoubleAt(3), m2.getDoubleAt(6)));
					temp[3] = fma(m1.getDoubleAt(2), m2.getDoubleAt(5), fma(m1.getDoubleAt(3), m2.getDoubleAt(7)));
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, fma(m1[0], m2[0], fma(m1[1], m2[2])));
				temp.setVectorAt(1, fma(m1[0], m2[1], fma(m1[1], m2[3])));
				temp.setVectorAt(2, fma(m1[2], m2[0], fma(m1[3], m2[2])));
				temp.setVectorAt(3, fma(m1[2], m2[1], fma(m1[3], m2[3])));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(2))));
					temp.setVectorAt(1, fma(m1[0], m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(3))));
					temp.setVectorAt(2, fma(m1[2], m2.getDoubleAt(0), fma(m1[3], m2.getDoubleAt(2))));
					temp.setVectorAt(3, fma(m1[2], m2.getDoubleAt(1), fma(m1[3], m2.getDoubleAt(3))));
				} else {
					temp.setVectorAt(4, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(2))));
					temp.setVectorAt(5, fma(m1[0], m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(3))));
					temp.setVectorAt(6, fma(m1[2], m2.getDoubleAt(0), fma(m1[3], m2.getDoubleAt(2))));
					temp.setVectorAt(7, fma(m1[2], m2.getDoubleAt(1), fma(m1[3], m2.getDoubleAt(3))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[2])));
					temp.setVectorAt(1, fma(m1.getDoubleAt(0), m2[1], fma(m1.getDoubleAt(1), m2[3])));
					temp.setVectorAt(2, fma(m1.getDoubleAt(2), m2[0], fma(m1.getDoubleAt(3), m2[2])));
					temp.setVectorAt(3, fma(m1.getDoubleAt(2), m2[1], fma(m1.getDoubleAt(3), m2[3])));
				} else {
					temp.setVectorAt(4, fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[2])));
					temp.setVectorAt(5, fma(m1.getDoubleAt(0), m2[1], fma(m1.getDoubleAt(1), m2[3])));
					temp.setVectorAt(6, fma(m1.getDoubleAt(2), m2[0], fma(m1.getDoubleAt(3), m2[2])));
					temp.setVectorAt(7, fma(m1.getDoubleAt(2), m2[1], fma(m1.getDoubleAt(3), m2[3])));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(2))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(0), m2.getDoubleAt(1), fma(m1.getDoubleAt(1), m2.getDoubleAt(3))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(2))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(2), m2.getDoubleAt(1), fma(m1.getDoubleAt(3), m2.getDoubleAt(3))));
					} else {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(4), fma(m1.getDoubleAt(1), m2.getDoubleAt(6))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(0), m2.getDoubleAt(5), fma(m1.getDoubleAt(1), m2.getDoubleAt(7))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(2), m2.getDoubleAt(4), fma(m1.getDoubleAt(3), m2.getDoubleAt(6))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(2), m2.getDoubleAt(5), fma(m1.getDoubleAt(3), m2.getDoubleAt(7))));
					}
				} else if(m2 != temp || m1 != temp) {
					temp.setVectorAt(4, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(2))));
					temp.setVectorAt(5, fma(m1.getDoubleAt(0), m2.getDoubleAt(1), fma(m1.getDoubleAt(1), m2.getDoubleAt(3))));
					temp.setVectorAt(6, fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(2))));
					temp.setVectorAt(7, fma(m1.getDoubleAt(2), m2.getDoubleAt(1), fma(m1.getDoubleAt(3), m2.getDoubleAt(3))));
				} else {
					temp.setVectorAt(8, fma(m1.getDoubleAt(0), m2.getDoubleAt(4), fma(m1.getDoubleAt(1), m2.getDoubleAt(6))));
					temp.setVectorAt(9, fma(m1.getDoubleAt(0), m2.getDoubleAt(5), fma(m1.getDoubleAt(1), m2.getDoubleAt(7))));
					temp.setVectorAt(10, fma(m1.getDoubleAt(2), m2.getDoubleAt(4), fma(m1.getDoubleAt(3), m2.getDoubleAt(6))));
					temp.setVectorAt(11, fma(m1.getDoubleAt(2), m2.getDoubleAt(5), fma(m1.getDoubleAt(3), m2.getDoubleAt(7))));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_2x2_2x1_FMA(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 2; final int nM1 = 2; final int mM2 = 2; final int nM2 = 1;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1[0], m2[0], fma(m1[1], m2[1]));
				temp[1] = fma(m1[2], m2[0], fma(m1[3], m2[1]));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(1)));
				temp[1] = fma(m1[2], m2.getDoubleAt(0), fma(m1[3], m2.getDoubleAt(1)));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[1]));
				temp[1] = fma(m1.getDoubleAt(2), m2[0], fma(m1.getDoubleAt(3), m2[1]));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1)));
					temp[1] = fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(1)));
				} else {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(4), fma(m1.getDoubleAt(1), m2.getDoubleAt(5)));
					temp[1] = fma(m1.getDoubleAt(2), m2.getDoubleAt(4), fma(m1.getDoubleAt(3), m2.getDoubleAt(5)));
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, fma(m1[0], m2[0], fma(m1[1], m2[1])));
				temp.setVectorAt(1, fma(m1[2], m2[0], fma(m1[3], m2[1])));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(1))));
					temp.setVectorAt(1, fma(m1[2], m2.getDoubleAt(0), fma(m1[3], m2.getDoubleAt(1))));
				} else {
					temp.setVectorAt(2, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(1))));
					temp.setVectorAt(3, fma(m1[2], m2.getDoubleAt(0), fma(m1[3], m2.getDoubleAt(1))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[1])));
					temp.setVectorAt(1, fma(m1.getDoubleAt(2), m2[0], fma(m1.getDoubleAt(3), m2[1])));
				} else {
					temp.setVectorAt(4, fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[1])));
					temp.setVectorAt(5, fma(m1.getDoubleAt(2), m2[0], fma(m1.getDoubleAt(3), m2[1])));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(1))));
					} else {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(4), fma(m1.getDoubleAt(1), m2.getDoubleAt(5))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(2), m2.getDoubleAt(4), fma(m1.getDoubleAt(3), m2.getDoubleAt(5))));
					}
				} else if(m1 != temp) {
					temp.setVectorAt(2, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1))));
					temp.setVectorAt(3, fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(1))));
				} else if(m2 != temp) {
					temp.setVectorAt(4, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1))));
					temp.setVectorAt(5, fma(m1.getDoubleAt(2), m2.getDoubleAt(0), fma(m1.getDoubleAt(3), m2.getDoubleAt(1))));
				} else {
					temp.setVectorAt(6, fma(m1.getDoubleAt(0), m2.getDoubleAt(4), fma(m1.getDoubleAt(1), m2.getDoubleAt(5))));
					temp.setVectorAt(7, fma(m1.getDoubleAt(2), m2.getDoubleAt(4), fma(m1.getDoubleAt(3), m2.getDoubleAt(5))));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_3x3_3x3_FMA(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 3; final int nM1 = 3; final int mM2 = 3; final int nM2 = 3;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1[0], m2[0], fma(m1[1], m2[3], fma(m1[2], m2[6])));
				temp[1] = fma(m1[0], m2[1], fma(m1[1], m2[4], fma(m1[2], m2[7])));
				temp[2] = fma(m1[0], m2[2], fma(m1[1], m2[5], fma(m1[2], m2[8])));
				temp[3] = fma(m1[3], m2[0], fma(m1[4], m2[3], fma(m1[5], m2[6])));
				temp[4] = fma(m1[3], m2[1], fma(m1[4], m2[4], fma(m1[5], m2[7])));
				temp[5] = fma(m1[3], m2[2], fma(m1[4], m2[5], fma(m1[5], m2[8])));
				temp[6] = fma(m1[6], m2[0], fma(m1[7], m2[3], fma(m1[8], m2[6])));
				temp[7] = fma(m1[6], m2[1], fma(m1[7], m2[4], fma(m1[8], m2[7])));
				temp[8] = fma(m1[6], m2[2], fma(m1[7], m2[5], fma(m1[8], m2[8])));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(3), fma(m1[2], m2.getDoubleAt(6))));
				temp[1] = fma(m1[0], m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(4), fma(m1[2], m2.getDoubleAt(7))));
				temp[2] = fma(m1[0], m2.getDoubleAt(2), fma(m1[1], m2.getDoubleAt(5), fma(m1[2], m2.getDoubleAt(8))));
				temp[3] = fma(m1[3], m2.getDoubleAt(0), fma(m1[4], m2.getDoubleAt(3), fma(m1[5], m2.getDoubleAt(6))));
				temp[4] = fma(m1[3], m2.getDoubleAt(1), fma(m1[4], m2.getDoubleAt(4), fma(m1[5], m2.getDoubleAt(7))));
				temp[5] = fma(m1[3], m2.getDoubleAt(2), fma(m1[4], m2.getDoubleAt(5), fma(m1[5], m2.getDoubleAt(8))));
				temp[6] = fma(m1[6], m2.getDoubleAt(0), fma(m1[7], m2.getDoubleAt(3), fma(m1[8], m2.getDoubleAt(6))));
				temp[7] = fma(m1[6], m2.getDoubleAt(1), fma(m1[7], m2.getDoubleAt(4), fma(m1[8], m2.getDoubleAt(7))));
				temp[8] = fma(m1[6], m2.getDoubleAt(2), fma(m1[7], m2.getDoubleAt(5), fma(m1[8], m2.getDoubleAt(8))));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[3], fma(m1.getDoubleAt(2), m2[6])));
				temp[1] = fma(m1.getDoubleAt(0), m2[1], fma(m1.getDoubleAt(1), m2[4], fma(m1.getDoubleAt(2), m2[7])));
				temp[2] = fma(m1.getDoubleAt(0), m2[2], fma(m1.getDoubleAt(1), m2[5], fma(m1.getDoubleAt(2), m2[8])));
				temp[3] = fma(m1.getDoubleAt(3), m2[0], fma(m1.getDoubleAt(4), m2[3], fma(m1.getDoubleAt(5), m2[6])));
				temp[4] = fma(m1.getDoubleAt(3), m2[1], fma(m1.getDoubleAt(4), m2[4], fma(m1.getDoubleAt(5), m2[7])));
				temp[5] = fma(m1.getDoubleAt(3), m2[2], fma(m1.getDoubleAt(4), m2[5], fma(m1.getDoubleAt(5), m2[8])));
				temp[6] = fma(m1.getDoubleAt(6), m2[0], fma(m1.getDoubleAt(7), m2[3], fma(m1.getDoubleAt(8), m2[6])));
				temp[7] = fma(m1.getDoubleAt(6), m2[1], fma(m1.getDoubleAt(7), m2[4], fma(m1.getDoubleAt(8), m2[7])));
				temp[8] = fma(m1.getDoubleAt(6), m2[2], fma(m1.getDoubleAt(7), m2[5], fma(m1.getDoubleAt(8), m2[8])));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(3), fma(m1.getDoubleAt(2), m2.getDoubleAt(6))));
					temp[1] = fma(m1.getDoubleAt(0), m2.getDoubleAt(1), fma(m1.getDoubleAt(1), m2.getDoubleAt(4), fma(m1.getDoubleAt(2), m2.getDoubleAt(7))));
					temp[2] = fma(m1.getDoubleAt(0), m2.getDoubleAt(2), fma(m1.getDoubleAt(1), m2.getDoubleAt(5), fma(m1.getDoubleAt(2), m2.getDoubleAt(8))));
					temp[3] = fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(3), fma(m1.getDoubleAt(5), m2.getDoubleAt(6))));
					temp[4] = fma(m1.getDoubleAt(3), m2.getDoubleAt(1), fma(m1.getDoubleAt(4), m2.getDoubleAt(4), fma(m1.getDoubleAt(5), m2.getDoubleAt(7))));
					temp[5] = fma(m1.getDoubleAt(3), m2.getDoubleAt(2), fma(m1.getDoubleAt(4), m2.getDoubleAt(5), fma(m1.getDoubleAt(5), m2.getDoubleAt(8))));
					temp[6] = fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(3), fma(m1.getDoubleAt(8), m2.getDoubleAt(6))));
					temp[7] = fma(m1.getDoubleAt(6), m2.getDoubleAt(1), fma(m1.getDoubleAt(7), m2.getDoubleAt(4), fma(m1.getDoubleAt(8), m2.getDoubleAt(7))));
					temp[8] = fma(m1.getDoubleAt(6), m2.getDoubleAt(2), fma(m1.getDoubleAt(7), m2.getDoubleAt(5), fma(m1.getDoubleAt(8), m2.getDoubleAt(8))));
				} else {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(9) , fma(m1.getDoubleAt(1), m2.getDoubleAt(12), fma(m1.getDoubleAt(2), m2.getDoubleAt(15))));
					temp[1] = fma(m1.getDoubleAt(0), m2.getDoubleAt(10), fma(m1.getDoubleAt(1), m2.getDoubleAt(13), fma(m1.getDoubleAt(2), m2.getDoubleAt(16))));
					temp[2] = fma(m1.getDoubleAt(0), m2.getDoubleAt(11), fma(m1.getDoubleAt(1), m2.getDoubleAt(14), fma(m1.getDoubleAt(2), m2.getDoubleAt(17))));
					temp[3] = fma(m1.getDoubleAt(3), m2.getDoubleAt(9) , fma(m1.getDoubleAt(4), m2.getDoubleAt(12), fma(m1.getDoubleAt(5), m2.getDoubleAt(15))));
					temp[4] = fma(m1.getDoubleAt(3), m2.getDoubleAt(10), fma(m1.getDoubleAt(4), m2.getDoubleAt(13), fma(m1.getDoubleAt(5), m2.getDoubleAt(16))));
					temp[5] = fma(m1.getDoubleAt(3), m2.getDoubleAt(11), fma(m1.getDoubleAt(4), m2.getDoubleAt(14), fma(m1.getDoubleAt(5), m2.getDoubleAt(17))));
					temp[6] = fma(m1.getDoubleAt(6), m2.getDoubleAt(9) , fma(m1.getDoubleAt(7), m2.getDoubleAt(12), fma(m1.getDoubleAt(8), m2.getDoubleAt(15))));
					temp[7] = fma(m1.getDoubleAt(6), m2.getDoubleAt(10), fma(m1.getDoubleAt(7), m2.getDoubleAt(13), fma(m1.getDoubleAt(8), m2.getDoubleAt(16))));
					temp[8] = fma(m1.getDoubleAt(6), m2.getDoubleAt(11), fma(m1.getDoubleAt(7), m2.getDoubleAt(14), fma(m1.getDoubleAt(8), m2.getDoubleAt(17))));
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, fma(m1[0], m2[0], fma(m1[1], m2[3], fma(m1[2], m2[6]))));
				temp.setVectorAt(1, fma(m1[0], m2[1], fma(m1[1], m2[4], fma(m1[2], m2[7]))));
				temp.setVectorAt(2, fma(m1[0], m2[2], fma(m1[1], m2[5], fma(m1[2], m2[8]))));
				temp.setVectorAt(3, fma(m1[3], m2[0], fma(m1[4], m2[3], fma(m1[5], m2[6]))));
				temp.setVectorAt(4, fma(m1[3], m2[1], fma(m1[4], m2[4], fma(m1[5], m2[7]))));
				temp.setVectorAt(5, fma(m1[3], m2[2], fma(m1[4], m2[5], fma(m1[5], m2[8]))));
				temp.setVectorAt(6, fma(m1[6], m2[0], fma(m1[7], m2[3], fma(m1[8], m2[6]))));
				temp.setVectorAt(7, fma(m1[6], m2[1], fma(m1[7], m2[4], fma(m1[8], m2[7]))));
				temp.setVectorAt(8, fma(m1[6], m2[2], fma(m1[7], m2[5], fma(m1[8], m2[8]))));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(3), fma(m1[2], m2.getDoubleAt(6)))));
					temp.setVectorAt(1, fma(m1[0], m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(4), fma(m1[2], m2.getDoubleAt(7)))));
					temp.setVectorAt(2, fma(m1[0], m2.getDoubleAt(2), fma(m1[1], m2.getDoubleAt(5), fma(m1[2], m2.getDoubleAt(8)))));
					temp.setVectorAt(3, fma(m1[3], m2.getDoubleAt(0), fma(m1[4], m2.getDoubleAt(3), fma(m1[5], m2.getDoubleAt(6)))));
					temp.setVectorAt(4, fma(m1[3], m2.getDoubleAt(1), fma(m1[4], m2.getDoubleAt(4), fma(m1[5], m2.getDoubleAt(7)))));
					temp.setVectorAt(5, fma(m1[3], m2.getDoubleAt(2), fma(m1[4], m2.getDoubleAt(5), fma(m1[5], m2.getDoubleAt(8)))));
					temp.setVectorAt(6, fma(m1[6], m2.getDoubleAt(0), fma(m1[7], m2.getDoubleAt(3), fma(m1[8], m2.getDoubleAt(6)))));
					temp.setVectorAt(7, fma(m1[6], m2.getDoubleAt(1), fma(m1[7], m2.getDoubleAt(4), fma(m1[8], m2.getDoubleAt(7)))));
					temp.setVectorAt(8, fma(m1[6], m2.getDoubleAt(2), fma(m1[7], m2.getDoubleAt(5), fma(m1[8], m2.getDoubleAt(8)))));
				} else {
					temp.setVectorAt(9 , fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(3), fma(m1[2], m2.getDoubleAt(6)))));
					temp.setVectorAt(10, fma(m1[0], m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(4), fma(m1[2], m2.getDoubleAt(7)))));
					temp.setVectorAt(11, fma(m1[0], m2.getDoubleAt(2), fma(m1[1], m2.getDoubleAt(5), fma(m1[2], m2.getDoubleAt(8)))));
					temp.setVectorAt(12, fma(m1[3], m2.getDoubleAt(0), fma(m1[4], m2.getDoubleAt(3), fma(m1[5], m2.getDoubleAt(6)))));
					temp.setVectorAt(13, fma(m1[3], m2.getDoubleAt(1), fma(m1[4], m2.getDoubleAt(4), fma(m1[5], m2.getDoubleAt(7)))));
					temp.setVectorAt(14, fma(m1[3], m2.getDoubleAt(2), fma(m1[4], m2.getDoubleAt(5), fma(m1[5], m2.getDoubleAt(8)))));
					temp.setVectorAt(15, fma(m1[6], m2.getDoubleAt(0), fma(m1[7], m2.getDoubleAt(3), fma(m1[8], m2.getDoubleAt(6)))));
					temp.setVectorAt(16, fma(m1[6], m2.getDoubleAt(1), fma(m1[7], m2.getDoubleAt(4), fma(m1[8], m2.getDoubleAt(7)))));
					temp.setVectorAt(17, fma(m1[6], m2.getDoubleAt(2), fma(m1[7], m2.getDoubleAt(5), fma(m1[8], m2.getDoubleAt(8)))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[3], fma(m1.getDoubleAt(2), m2[6]))));
					temp.setVectorAt(1, fma(m1.getDoubleAt(0), m2[1], fma(m1.getDoubleAt(1), m2[4], fma(m1.getDoubleAt(2), m2[7]))));
					temp.setVectorAt(2, fma(m1.getDoubleAt(0), m2[2], fma(m1.getDoubleAt(1), m2[5], fma(m1.getDoubleAt(2), m2[8]))));
					temp.setVectorAt(3, fma(m1.getDoubleAt(3), m2[0], fma(m1.getDoubleAt(4), m2[3], fma(m1.getDoubleAt(5), m2[6]))));
					temp.setVectorAt(4, fma(m1.getDoubleAt(3), m2[1], fma(m1.getDoubleAt(4), m2[4], fma(m1.getDoubleAt(5), m2[7]))));
					temp.setVectorAt(5, fma(m1.getDoubleAt(3), m2[2], fma(m1.getDoubleAt(4), m2[5], fma(m1.getDoubleAt(5), m2[8]))));
					temp.setVectorAt(6, fma(m1.getDoubleAt(6), m2[0], fma(m1.getDoubleAt(7), m2[3], fma(m1.getDoubleAt(8), m2[6]))));
					temp.setVectorAt(7, fma(m1.getDoubleAt(6), m2[1], fma(m1.getDoubleAt(7), m2[4], fma(m1.getDoubleAt(8), m2[7]))));
					temp.setVectorAt(8, fma(m1.getDoubleAt(6), m2[2], fma(m1.getDoubleAt(7), m2[5], fma(m1.getDoubleAt(8), m2[8]))));
				} else {
					temp.setVectorAt(9 , fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[3], fma(m1.getDoubleAt(2), m2[6]))));
					temp.setVectorAt(10, fma(m1.getDoubleAt(0), m2[1], fma(m1.getDoubleAt(1), m2[4], fma(m1.getDoubleAt(2), m2[7]))));
					temp.setVectorAt(11, fma(m1.getDoubleAt(0), m2[2], fma(m1.getDoubleAt(1), m2[5], fma(m1.getDoubleAt(2), m2[8]))));
					temp.setVectorAt(12, fma(m1.getDoubleAt(3), m2[0], fma(m1.getDoubleAt(4), m2[3], fma(m1.getDoubleAt(5), m2[6]))));
					temp.setVectorAt(13, fma(m1.getDoubleAt(3), m2[1], fma(m1.getDoubleAt(4), m2[4], fma(m1.getDoubleAt(5), m2[7]))));
					temp.setVectorAt(14, fma(m1.getDoubleAt(3), m2[2], fma(m1.getDoubleAt(4), m2[5], fma(m1.getDoubleAt(5), m2[8]))));
					temp.setVectorAt(15, fma(m1.getDoubleAt(6), m2[0], fma(m1.getDoubleAt(7), m2[3], fma(m1.getDoubleAt(8), m2[6]))));
					temp.setVectorAt(16, fma(m1.getDoubleAt(6), m2[1], fma(m1.getDoubleAt(7), m2[4], fma(m1.getDoubleAt(8), m2[7]))));
					temp.setVectorAt(17, fma(m1.getDoubleAt(6), m2[2], fma(m1.getDoubleAt(7), m2[5], fma(m1.getDoubleAt(8), m2[8]))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(3), fma(m1.getDoubleAt(2), m2.getDoubleAt(6)))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(0), m2.getDoubleAt(1), fma(m1.getDoubleAt(1), m2.getDoubleAt(4), fma(m1.getDoubleAt(2), m2.getDoubleAt(7)))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(0), m2.getDoubleAt(2), fma(m1.getDoubleAt(1), m2.getDoubleAt(5), fma(m1.getDoubleAt(2), m2.getDoubleAt(8)))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(3), fma(m1.getDoubleAt(5), m2.getDoubleAt(6)))));
						temp.setVectorAt(4, fma(m1.getDoubleAt(3), m2.getDoubleAt(1), fma(m1.getDoubleAt(4), m2.getDoubleAt(4), fma(m1.getDoubleAt(5), m2.getDoubleAt(7)))));
						temp.setVectorAt(5, fma(m1.getDoubleAt(3), m2.getDoubleAt(2), fma(m1.getDoubleAt(4), m2.getDoubleAt(5), fma(m1.getDoubleAt(5), m2.getDoubleAt(8)))));
						temp.setVectorAt(6, fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(3), fma(m1.getDoubleAt(8), m2.getDoubleAt(6)))));
						temp.setVectorAt(7, fma(m1.getDoubleAt(6), m2.getDoubleAt(1), fma(m1.getDoubleAt(7), m2.getDoubleAt(4), fma(m1.getDoubleAt(8), m2.getDoubleAt(7)))));
						temp.setVectorAt(8, fma(m1.getDoubleAt(6), m2.getDoubleAt(2), fma(m1.getDoubleAt(7), m2.getDoubleAt(5), fma(m1.getDoubleAt(8), m2.getDoubleAt(8)))));
					} else {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(9) , fma(m1.getDoubleAt(1), m2.getDoubleAt(12), fma(m1.getDoubleAt(2), m2.getDoubleAt(15)))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(0), m2.getDoubleAt(10), fma(m1.getDoubleAt(1), m2.getDoubleAt(13), fma(m1.getDoubleAt(2), m2.getDoubleAt(16)))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(0), m2.getDoubleAt(11), fma(m1.getDoubleAt(1), m2.getDoubleAt(14), fma(m1.getDoubleAt(2), m2.getDoubleAt(17)))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(3), m2.getDoubleAt(9) , fma(m1.getDoubleAt(4), m2.getDoubleAt(12), fma(m1.getDoubleAt(5), m2.getDoubleAt(15)))));
						temp.setVectorAt(4, fma(m1.getDoubleAt(3), m2.getDoubleAt(10), fma(m1.getDoubleAt(4), m2.getDoubleAt(13), fma(m1.getDoubleAt(5), m2.getDoubleAt(16)))));
						temp.setVectorAt(5, fma(m1.getDoubleAt(3), m2.getDoubleAt(11), fma(m1.getDoubleAt(4), m2.getDoubleAt(14), fma(m1.getDoubleAt(5), m2.getDoubleAt(17)))));
						temp.setVectorAt(6, fma(m1.getDoubleAt(6), m2.getDoubleAt(9) , fma(m1.getDoubleAt(7), m2.getDoubleAt(12), fma(m1.getDoubleAt(8), m2.getDoubleAt(15)))));
						temp.setVectorAt(7, fma(m1.getDoubleAt(6), m2.getDoubleAt(10), fma(m1.getDoubleAt(7), m2.getDoubleAt(13), fma(m1.getDoubleAt(8), m2.getDoubleAt(16)))));
						temp.setVectorAt(8, fma(m1.getDoubleAt(6), m2.getDoubleAt(11), fma(m1.getDoubleAt(7), m2.getDoubleAt(14), fma(m1.getDoubleAt(8), m2.getDoubleAt(17)))));
					}
				} else if(m2 != temp || m1 != temp) {
					temp.setVectorAt(9 , fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(3), fma(m1.getDoubleAt(2), m2.getDoubleAt(6)))));
					temp.setVectorAt(10, fma(m1.getDoubleAt(0), m2.getDoubleAt(1), fma(m1.getDoubleAt(1), m2.getDoubleAt(4), fma(m1.getDoubleAt(2), m2.getDoubleAt(7)))));
					temp.setVectorAt(11, fma(m1.getDoubleAt(0), m2.getDoubleAt(2), fma(m1.getDoubleAt(1), m2.getDoubleAt(5), fma(m1.getDoubleAt(2), m2.getDoubleAt(8)))));
					temp.setVectorAt(12, fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(3), fma(m1.getDoubleAt(5), m2.getDoubleAt(6)))));
					temp.setVectorAt(13, fma(m1.getDoubleAt(3), m2.getDoubleAt(1), fma(m1.getDoubleAt(4), m2.getDoubleAt(4), fma(m1.getDoubleAt(5), m2.getDoubleAt(7)))));
					temp.setVectorAt(14, fma(m1.getDoubleAt(3), m2.getDoubleAt(2), fma(m1.getDoubleAt(4), m2.getDoubleAt(5), fma(m1.getDoubleAt(5), m2.getDoubleAt(8)))));
					temp.setVectorAt(15, fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(3), fma(m1.getDoubleAt(8), m2.getDoubleAt(6)))));
					temp.setVectorAt(16, fma(m1.getDoubleAt(6), m2.getDoubleAt(1), fma(m1.getDoubleAt(7), m2.getDoubleAt(4), fma(m1.getDoubleAt(8), m2.getDoubleAt(7)))));
					temp.setVectorAt(17, fma(m1.getDoubleAt(6), m2.getDoubleAt(2), fma(m1.getDoubleAt(7), m2.getDoubleAt(5), fma(m1.getDoubleAt(8), m2.getDoubleAt(8)))));
				} else {
					temp.setVectorAt(18, fma(m1.getDoubleAt(0), m2.getDoubleAt(9) , fma(m1.getDoubleAt(1), m2.getDoubleAt(12), fma(m1.getDoubleAt(2), m2.getDoubleAt(15)))));
					temp.setVectorAt(19, fma(m1.getDoubleAt(0), m2.getDoubleAt(10), fma(m1.getDoubleAt(1), m2.getDoubleAt(13), fma(m1.getDoubleAt(2), m2.getDoubleAt(16)))));
					temp.setVectorAt(20, fma(m1.getDoubleAt(0), m2.getDoubleAt(11), fma(m1.getDoubleAt(1), m2.getDoubleAt(14), fma(m1.getDoubleAt(2), m2.getDoubleAt(17)))));
					temp.setVectorAt(21, fma(m1.getDoubleAt(3), m2.getDoubleAt(9) , fma(m1.getDoubleAt(4), m2.getDoubleAt(12), fma(m1.getDoubleAt(5), m2.getDoubleAt(15)))));
					temp.setVectorAt(22, fma(m1.getDoubleAt(3), m2.getDoubleAt(10), fma(m1.getDoubleAt(4), m2.getDoubleAt(13), fma(m1.getDoubleAt(5), m2.getDoubleAt(16)))));
					temp.setVectorAt(23, fma(m1.getDoubleAt(3), m2.getDoubleAt(11), fma(m1.getDoubleAt(4), m2.getDoubleAt(14), fma(m1.getDoubleAt(5), m2.getDoubleAt(17)))));
					temp.setVectorAt(24, fma(m1.getDoubleAt(6), m2.getDoubleAt(9) , fma(m1.getDoubleAt(7), m2.getDoubleAt(12), fma(m1.getDoubleAt(8), m2.getDoubleAt(15)))));
					temp.setVectorAt(25, fma(m1.getDoubleAt(6), m2.getDoubleAt(10), fma(m1.getDoubleAt(7), m2.getDoubleAt(13), fma(m1.getDoubleAt(8), m2.getDoubleAt(16)))));
					temp.setVectorAt(26, fma(m1.getDoubleAt(6), m2.getDoubleAt(11), fma(m1.getDoubleAt(7), m2.getDoubleAt(14), fma(m1.getDoubleAt(8), m2.getDoubleAt(17)))));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_3x3_3x1_FMA(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 3; final int nM1 = 3; final int mM2 = 3; final int nM2 = 1;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1[0], m2[0], fma(m1[1], m2[1], fma(m1[2], m2[2])));
				temp[1] = fma(m1[3], m2[0], fma(m1[4], m2[1], fma(m1[5], m2[2])));
				temp[2] = fma(m1[6], m2[0], fma(m1[7], m2[1], fma(m1[8], m2[2])));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(1), fma(m1[2], m2.getDoubleAt(2))));
				temp[1] = fma(m1[3], m2.getDoubleAt(0), fma(m1[4], m2.getDoubleAt(1), fma(m1[5], m2.getDoubleAt(2))));
				temp[2] = fma(m1[6], m2.getDoubleAt(0), fma(m1[7], m2.getDoubleAt(1), fma(m1[8], m2.getDoubleAt(2))));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[1], fma(m1.getDoubleAt(2), m2[2])));
				temp[1] = fma(m1.getDoubleAt(3), m2[0], fma(m1.getDoubleAt(4), m2[1], fma(m1.getDoubleAt(5), m2[2])));
				temp[2] = fma(m1.getDoubleAt(6), m2[0], fma(m1.getDoubleAt(7), m2[1], fma(m1.getDoubleAt(8), m2[2])));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1), fma(m1.getDoubleAt(2), m2.getDoubleAt(2))));
					temp[1] = fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(1), fma(m1.getDoubleAt(5), m2.getDoubleAt(2))));
					temp[2] = fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(1), fma(m1.getDoubleAt(8), m2.getDoubleAt(2))));
				} else {
					temp[0] = fma(m1.getDoubleAt(0), m2.getDoubleAt(9), fma(m1.getDoubleAt(1), m2.getDoubleAt(10), fma(m1.getDoubleAt(2), m2.getDoubleAt(11))));
					temp[1] = fma(m1.getDoubleAt(3), m2.getDoubleAt(9), fma(m1.getDoubleAt(4), m2.getDoubleAt(10), fma(m1.getDoubleAt(5), m2.getDoubleAt(11))));
					temp[2] = fma(m1.getDoubleAt(6), m2.getDoubleAt(9), fma(m1.getDoubleAt(7), m2.getDoubleAt(10), fma(m1.getDoubleAt(8), m2.getDoubleAt(11))));
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, fma(m1[0], m2[0], fma(m1[1], m2[1], fma(m1[2], m2[2]))));
				temp.setVectorAt(1, fma(m1[3], m2[0], fma(m1[4], m2[1], fma(m1[5], m2[2]))));
				temp.setVectorAt(2, fma(m1[6], m2[0], fma(m1[7], m2[1], fma(m1[8], m2[2]))));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(1), fma(m1[2], m2.getDoubleAt(2)))));
					temp.setVectorAt(1, fma(m1[3], m2.getDoubleAt(0), fma(m1[4], m2.getDoubleAt(1), fma(m1[5], m2.getDoubleAt(2)))));
					temp.setVectorAt(2, fma(m1[6], m2.getDoubleAt(0), fma(m1[7], m2.getDoubleAt(1), fma(m1[8], m2.getDoubleAt(2)))));
				} else {
					temp.setVectorAt(3, fma(m1[0], m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(1), fma(m1[2], m2.getDoubleAt(2)))));
					temp.setVectorAt(4, fma(m1[3], m2.getDoubleAt(0), fma(m1[4], m2.getDoubleAt(1), fma(m1[5], m2.getDoubleAt(2)))));
					temp.setVectorAt(5, fma(m1[6], m2.getDoubleAt(0), fma(m1[7], m2.getDoubleAt(1), fma(m1[8], m2.getDoubleAt(2)))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[1], fma(m1.getDoubleAt(2), m2[2]))));
					temp.setVectorAt(1, fma(m1.getDoubleAt(3), m2[0], fma(m1.getDoubleAt(4), m2[1], fma(m1.getDoubleAt(5), m2[2]))));
					temp.setVectorAt(2, fma(m1.getDoubleAt(6), m2[0], fma(m1.getDoubleAt(7), m2[1], fma(m1.getDoubleAt(8), m2[2]))));
				} else {
					temp.setVectorAt(9 , fma(m1.getDoubleAt(0), m2[0], fma(m1.getDoubleAt(1), m2[1], fma(m1.getDoubleAt(2), m2[2]))));
					temp.setVectorAt(10, fma(m1.getDoubleAt(3), m2[0], fma(m1.getDoubleAt(4), m2[1], fma(m1.getDoubleAt(5), m2[2]))));
					temp.setVectorAt(11, fma(m1.getDoubleAt(6), m2[0], fma(m1.getDoubleAt(7), m2[1], fma(m1.getDoubleAt(8), m2[2]))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1), fma(m1.getDoubleAt(2), m2.getDoubleAt(2)))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(1), fma(m1.getDoubleAt(5), m2.getDoubleAt(2)))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(1), fma(m1.getDoubleAt(8), m2.getDoubleAt(2)))));
					} else {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0), m2.getDoubleAt(9), fma(m1.getDoubleAt(1), m2.getDoubleAt(10), fma(m1.getDoubleAt(2), m2.getDoubleAt(11)))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(3), m2.getDoubleAt(9), fma(m1.getDoubleAt(4), m2.getDoubleAt(10), fma(m1.getDoubleAt(5), m2.getDoubleAt(11)))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(6), m2.getDoubleAt(9), fma(m1.getDoubleAt(7), m2.getDoubleAt(10), fma(m1.getDoubleAt(8), m2.getDoubleAt(11)))));
					}
				} else if(m1 != temp) {
					temp.setVectorAt(3, fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1), fma(m1.getDoubleAt(2), m2.getDoubleAt(2)))));
					temp.setVectorAt(4, fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(1), fma(m1.getDoubleAt(5), m2.getDoubleAt(2)))));
					temp.setVectorAt(5, fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(1), fma(m1.getDoubleAt(8), m2.getDoubleAt(2)))));
				} else if(m2 != temp) {
					temp.setVectorAt(9 , fma(m1.getDoubleAt(0), m2.getDoubleAt(0), fma(m1.getDoubleAt(1), m2.getDoubleAt(1), fma(m1.getDoubleAt(2), m2.getDoubleAt(2)))));
					temp.setVectorAt(10, fma(m1.getDoubleAt(3), m2.getDoubleAt(0), fma(m1.getDoubleAt(4), m2.getDoubleAt(1), fma(m1.getDoubleAt(5), m2.getDoubleAt(2)))));
					temp.setVectorAt(11, fma(m1.getDoubleAt(6), m2.getDoubleAt(0), fma(m1.getDoubleAt(7), m2.getDoubleAt(1), fma(m1.getDoubleAt(8), m2.getDoubleAt(2)))));
				} else {
					temp.setVectorAt(12, fma(m1.getDoubleAt(0), m2.getDoubleAt(9), fma(m1.getDoubleAt(1), m2.getDoubleAt(10), fma(m1.getDoubleAt(2), m2.getDoubleAt(11)))));
					temp.setVectorAt(13, fma(m1.getDoubleAt(3), m2.getDoubleAt(9), fma(m1.getDoubleAt(4), m2.getDoubleAt(10), fma(m1.getDoubleAt(5), m2.getDoubleAt(11)))));
					temp.setVectorAt(14, fma(m1.getDoubleAt(6), m2.getDoubleAt(9), fma(m1.getDoubleAt(7), m2.getDoubleAt(10), fma(m1.getDoubleAt(8), m2.getDoubleAt(11)))));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_4x4_4x4_FMA(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 4; final int nM1 = 4; final int mM2 = 4; final int nM2 = 4;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0]  = fma(m1[0] , m2[0], fma(m1[1] , m2[4], fma(m1[2] , m2[8] , fma(m1[3] , m2[12]))));
				temp[1]  = fma(m1[0] , m2[1], fma(m1[1] , m2[5], fma(m1[2] , m2[9] , fma(m1[3] , m2[13]))));
				temp[2]  = fma(m1[0] , m2[2], fma(m1[1] , m2[6], fma(m1[2] , m2[10], fma(m1[3] , m2[14]))));
				temp[3]  = fma(m1[0] , m2[3], fma(m1[1] , m2[7], fma(m1[2] , m2[11], fma(m1[3] , m2[15]))));
				temp[4]  = fma(m1[4] , m2[0], fma(m1[5] , m2[4], fma(m1[6] , m2[8] , fma(m1[7] , m2[12]))));
				temp[5]  = fma(m1[4] , m2[1], fma(m1[5] , m2[5], fma(m1[6] , m2[9] , fma(m1[7] , m2[13]))));
				temp[6]  = fma(m1[4] , m2[2], fma(m1[5] , m2[6], fma(m1[6] , m2[10], fma(m1[7] , m2[14]))));
				temp[7]  = fma(m1[4] , m2[3], fma(m1[5] , m2[7], fma(m1[6] , m2[11], fma(m1[7] , m2[15]))));
				temp[8]  = fma(m1[8] , m2[0], fma(m1[9] , m2[4], fma(m1[10], m2[8] , fma(m1[11], m2[12]))));
				temp[9]  = fma(m1[8] , m2[1], fma(m1[9] , m2[5], fma(m1[10], m2[9] , fma(m1[11], m2[13]))));
				temp[10] = fma(m1[8] , m2[2], fma(m1[9] , m2[6], fma(m1[10], m2[10], fma(m1[11], m2[14]))));
				temp[11] = fma(m1[8] , m2[3], fma(m1[9] , m2[7], fma(m1[10], m2[11], fma(m1[11], m2[15]))));
				temp[12] = fma(m1[12], m2[0], fma(m1[13], m2[4], fma(m1[14], m2[8] , fma(m1[15], m2[12]))));
				temp[13] = fma(m1[12], m2[1], fma(m1[13], m2[5], fma(m1[14], m2[9] , fma(m1[15], m2[13]))));
				temp[14] = fma(m1[12], m2[2], fma(m1[13], m2[6], fma(m1[14], m2[10], fma(m1[15], m2[14]))));
				temp[15] = fma(m1[12], m2[3], fma(m1[13], m2[7], fma(m1[14], m2[11], fma(m1[15], m2[15]))));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = fma(m1[0]  , m2.getDoubleAt(0), fma(m1[1] , m2.getDoubleAt(4), fma(m1[2] , m2.getDoubleAt(8) , fma(m1[3] , m2.getDoubleAt(12)))));
				temp[1] = fma(m1[0]  , m2.getDoubleAt(1), fma(m1[1] , m2.getDoubleAt(5), fma(m1[2] , m2.getDoubleAt(9) , fma(m1[3] , m2.getDoubleAt(13)))));
				temp[2] = fma(m1[0]  , m2.getDoubleAt(2), fma(m1[1] , m2.getDoubleAt(6), fma(m1[2] , m2.getDoubleAt(10), fma(m1[3] , m2.getDoubleAt(14)))));
				temp[3] = fma(m1[0]  , m2.getDoubleAt(3), fma(m1[1] , m2.getDoubleAt(7), fma(m1[2] , m2.getDoubleAt(11), fma(m1[3] , m2.getDoubleAt(15)))));
				temp[4] = fma(m1[4]  , m2.getDoubleAt(0), fma(m1[5] , m2.getDoubleAt(4), fma(m1[6] , m2.getDoubleAt(8) , fma(m1[7] , m2.getDoubleAt(12)))));
				temp[5] = fma(m1[4]  , m2.getDoubleAt(1), fma(m1[5] , m2.getDoubleAt(5), fma(m1[6] , m2.getDoubleAt(9) , fma(m1[7] , m2.getDoubleAt(13)))));
				temp[6] = fma(m1[4]  , m2.getDoubleAt(2), fma(m1[5] , m2.getDoubleAt(6), fma(m1[6] , m2.getDoubleAt(10), fma(m1[7] , m2.getDoubleAt(14)))));
				temp[7] = fma(m1[4]  , m2.getDoubleAt(3), fma(m1[5] , m2.getDoubleAt(7), fma(m1[6] , m2.getDoubleAt(11), fma(m1[7] , m2.getDoubleAt(15)))));
				temp[8] = fma(m1[8]  , m2.getDoubleAt(0), fma(m1[9] , m2.getDoubleAt(4), fma(m1[10], m2.getDoubleAt(8) , fma(m1[11], m2.getDoubleAt(12)))));
				temp[9] = fma(m1[8]  , m2.getDoubleAt(1), fma(m1[9] , m2.getDoubleAt(5), fma(m1[10], m2.getDoubleAt(9) , fma(m1[11], m2.getDoubleAt(13)))));
				temp[10] = fma(m1[8] , m2.getDoubleAt(2), fma(m1[9] , m2.getDoubleAt(6), fma(m1[10], m2.getDoubleAt(10), fma(m1[11], m2.getDoubleAt(14)))));
				temp[11] = fma(m1[8] , m2.getDoubleAt(3), fma(m1[9] , m2.getDoubleAt(7), fma(m1[10], m2.getDoubleAt(11), fma(m1[11], m2.getDoubleAt(15)))));
				temp[12] = fma(m1[12], m2.getDoubleAt(0), fma(m1[13], m2.getDoubleAt(4), fma(m1[14], m2.getDoubleAt(8) , fma(m1[15], m2.getDoubleAt(12)))));
				temp[13] = fma(m1[12], m2.getDoubleAt(1), fma(m1[13], m2.getDoubleAt(5), fma(m1[14], m2.getDoubleAt(9) , fma(m1[15], m2.getDoubleAt(13)))));
				temp[14] = fma(m1[12], m2.getDoubleAt(2), fma(m1[13], m2.getDoubleAt(6), fma(m1[14], m2.getDoubleAt(10), fma(m1[15], m2.getDoubleAt(14)))));
				temp[15] = fma(m1[12], m2.getDoubleAt(3), fma(m1[13], m2.getDoubleAt(7), fma(m1[14], m2.getDoubleAt(11), fma(m1[15], m2.getDoubleAt(15)))));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1.getDoubleAt(0)  , m2[0], fma(m1.getDoubleAt(1) , m2[4], fma(m1.getDoubleAt(2) , m2[8] , fma(m1.getDoubleAt(3) , m2[12]))));
				temp[1] = fma(m1.getDoubleAt(0)  , m2[1], fma(m1.getDoubleAt(1) , m2[5], fma(m1.getDoubleAt(2) , m2[9] , fma(m1.getDoubleAt(3) , m2[13]))));
				temp[2] = fma(m1.getDoubleAt(0)  , m2[2], fma(m1.getDoubleAt(1) , m2[6], fma(m1.getDoubleAt(2) , m2[10], fma(m1.getDoubleAt(3) , m2[14]))));
				temp[3] = fma(m1.getDoubleAt(0)  , m2[3], fma(m1.getDoubleAt(1) , m2[7], fma(m1.getDoubleAt(2) , m2[11], fma(m1.getDoubleAt(3) , m2[15]))));
				temp[4] = fma(m1.getDoubleAt(4)  , m2[0], fma(m1.getDoubleAt(5) , m2[4], fma(m1.getDoubleAt(6) , m2[8] , fma(m1.getDoubleAt(7) , m2[12]))));
				temp[5] = fma(m1.getDoubleAt(4)  , m2[1], fma(m1.getDoubleAt(5) , m2[5], fma(m1.getDoubleAt(6) , m2[9] , fma(m1.getDoubleAt(7) , m2[13]))));
				temp[6] = fma(m1.getDoubleAt(4)  , m2[2], fma(m1.getDoubleAt(5) , m2[6], fma(m1.getDoubleAt(6) , m2[10], fma(m1.getDoubleAt(7) , m2[14]))));
				temp[7] = fma(m1.getDoubleAt(4)  , m2[3], fma(m1.getDoubleAt(5) , m2[7], fma(m1.getDoubleAt(6) , m2[11], fma(m1.getDoubleAt(7) , m2[15]))));
				temp[8] = fma(m1.getDoubleAt(8)  , m2[0], fma(m1.getDoubleAt(9) , m2[4], fma(m1.getDoubleAt(10), m2[8] , fma(m1.getDoubleAt(11), m2[12]))));
				temp[9] = fma(m1.getDoubleAt(8)  , m2[1], fma(m1.getDoubleAt(9) , m2[5], fma(m1.getDoubleAt(10), m2[9] , fma(m1.getDoubleAt(11), m2[13]))));
				temp[10] = fma(m1.getDoubleAt(8) , m2[2], fma(m1.getDoubleAt(9) , m2[6], fma(m1.getDoubleAt(10), m2[10], fma(m1.getDoubleAt(11), m2[14]))));
				temp[11] = fma(m1.getDoubleAt(8) , m2[3], fma(m1.getDoubleAt(9) , m2[7], fma(m1.getDoubleAt(10), m2[11], fma(m1.getDoubleAt(11), m2[15]))));
				temp[12] = fma(m1.getDoubleAt(12), m2[0], fma(m1.getDoubleAt(13), m2[4], fma(m1.getDoubleAt(14), m2[8] , fma(m1.getDoubleAt(15), m2[12]))));
				temp[13] = fma(m1.getDoubleAt(12), m2[1], fma(m1.getDoubleAt(13), m2[5], fma(m1.getDoubleAt(14), m2[9] , fma(m1.getDoubleAt(15), m2[13]))));
				temp[14] = fma(m1.getDoubleAt(12), m2[2], fma(m1.getDoubleAt(13), m2[6], fma(m1.getDoubleAt(14), m2[10], fma(m1.getDoubleAt(15), m2[14]))));
				temp[15] = fma(m1.getDoubleAt(12), m2[3], fma(m1.getDoubleAt(13), m2[7], fma(m1.getDoubleAt(14), m2[11], fma(m1.getDoubleAt(15), m2[15]))));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(4), fma(m1.getDoubleAt(2) , m2.getDoubleAt(8) , fma(m1.getDoubleAt(3) , m2.getDoubleAt(12)))));
					temp[1] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(1), fma(m1.getDoubleAt(1) , m2.getDoubleAt(5), fma(m1.getDoubleAt(2) , m2.getDoubleAt(9) , fma(m1.getDoubleAt(3) , m2.getDoubleAt(13)))));
					temp[2] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(2), fma(m1.getDoubleAt(1) , m2.getDoubleAt(6), fma(m1.getDoubleAt(2) , m2.getDoubleAt(10), fma(m1.getDoubleAt(3) , m2.getDoubleAt(14)))));
					temp[3] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(3), fma(m1.getDoubleAt(1) , m2.getDoubleAt(7), fma(m1.getDoubleAt(2) , m2.getDoubleAt(11), fma(m1.getDoubleAt(3) , m2.getDoubleAt(15)))));
					temp[4] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(4), fma(m1.getDoubleAt(6) , m2.getDoubleAt(8) , fma(m1.getDoubleAt(7) , m2.getDoubleAt(12)))));
					temp[5] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(1), fma(m1.getDoubleAt(5) , m2.getDoubleAt(5), fma(m1.getDoubleAt(6) , m2.getDoubleAt(9) , fma(m1.getDoubleAt(7) , m2.getDoubleAt(13)))));
					temp[6] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(2), fma(m1.getDoubleAt(5) , m2.getDoubleAt(6), fma(m1.getDoubleAt(6) , m2.getDoubleAt(10), fma(m1.getDoubleAt(7) , m2.getDoubleAt(14)))));
					temp[7] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(3), fma(m1.getDoubleAt(5) , m2.getDoubleAt(7), fma(m1.getDoubleAt(6) , m2.getDoubleAt(11), fma(m1.getDoubleAt(7) , m2.getDoubleAt(15)))));
					temp[8] = fma(m1.getDoubleAt(8)  , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(4), fma(m1.getDoubleAt(10), m2.getDoubleAt(8) , fma(m1.getDoubleAt(11), m2.getDoubleAt(12)))));
					temp[9] = fma(m1.getDoubleAt(8)  , m2.getDoubleAt(1), fma(m1.getDoubleAt(9) , m2.getDoubleAt(5), fma(m1.getDoubleAt(10), m2.getDoubleAt(9) , fma(m1.getDoubleAt(11), m2.getDoubleAt(13)))));
					temp[10] = fma(m1.getDoubleAt(8) , m2.getDoubleAt(2), fma(m1.getDoubleAt(9) , m2.getDoubleAt(6), fma(m1.getDoubleAt(10), m2.getDoubleAt(10), fma(m1.getDoubleAt(11), m2.getDoubleAt(14)))));
					temp[11] = fma(m1.getDoubleAt(8) , m2.getDoubleAt(3), fma(m1.getDoubleAt(9) , m2.getDoubleAt(7), fma(m1.getDoubleAt(10), m2.getDoubleAt(11), fma(m1.getDoubleAt(11), m2.getDoubleAt(15)))));
					temp[12] = fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(4), fma(m1.getDoubleAt(14), m2.getDoubleAt(8) , fma(m1.getDoubleAt(15), m2.getDoubleAt(12)))));
					temp[13] = fma(m1.getDoubleAt(12), m2.getDoubleAt(1), fma(m1.getDoubleAt(13), m2.getDoubleAt(5), fma(m1.getDoubleAt(14), m2.getDoubleAt(9) , fma(m1.getDoubleAt(15), m2.getDoubleAt(13)))));
					temp[14] = fma(m1.getDoubleAt(12), m2.getDoubleAt(2), fma(m1.getDoubleAt(13), m2.getDoubleAt(6), fma(m1.getDoubleAt(14), m2.getDoubleAt(10), fma(m1.getDoubleAt(15), m2.getDoubleAt(14)))));
					temp[15] = fma(m1.getDoubleAt(12), m2.getDoubleAt(3), fma(m1.getDoubleAt(13), m2.getDoubleAt(7), fma(m1.getDoubleAt(14), m2.getDoubleAt(11), fma(m1.getDoubleAt(15), m2.getDoubleAt(15)))));
				} else {
					temp[0] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(16), fma(m1.getDoubleAt(1) , m2.getDoubleAt(20), fma(m1.getDoubleAt(2) , m2.getDoubleAt(24), fma(m1.getDoubleAt(3) , m2.getDoubleAt(28)))));
					temp[1] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(17), fma(m1.getDoubleAt(1) , m2.getDoubleAt(21), fma(m1.getDoubleAt(2) , m2.getDoubleAt(25), fma(m1.getDoubleAt(3) , m2.getDoubleAt(29)))));
					temp[2] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(18), fma(m1.getDoubleAt(1) , m2.getDoubleAt(22), fma(m1.getDoubleAt(2) , m2.getDoubleAt(26), fma(m1.getDoubleAt(3) , m2.getDoubleAt(30)))));
					temp[3] = fma(m1.getDoubleAt(0)  , m2.getDoubleAt(19), fma(m1.getDoubleAt(1) , m2.getDoubleAt(23), fma(m1.getDoubleAt(2) , m2.getDoubleAt(27), fma(m1.getDoubleAt(3) , m2.getDoubleAt(31)))));
					temp[4] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(16), fma(m1.getDoubleAt(5) , m2.getDoubleAt(20), fma(m1.getDoubleAt(6) , m2.getDoubleAt(24), fma(m1.getDoubleAt(7) , m2.getDoubleAt(28)))));
					temp[5] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(17), fma(m1.getDoubleAt(5) , m2.getDoubleAt(21), fma(m1.getDoubleAt(6) , m2.getDoubleAt(25), fma(m1.getDoubleAt(7) , m2.getDoubleAt(29)))));
					temp[6] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(18), fma(m1.getDoubleAt(5) , m2.getDoubleAt(22), fma(m1.getDoubleAt(6) , m2.getDoubleAt(26), fma(m1.getDoubleAt(7) , m2.getDoubleAt(30)))));
					temp[7] = fma(m1.getDoubleAt(4)  , m2.getDoubleAt(19), fma(m1.getDoubleAt(5) , m2.getDoubleAt(23), fma(m1.getDoubleAt(6) , m2.getDoubleAt(27), fma(m1.getDoubleAt(7) , m2.getDoubleAt(31)))));
					temp[8] = fma(m1.getDoubleAt(8)  , m2.getDoubleAt(16), fma(m1.getDoubleAt(9) , m2.getDoubleAt(20), fma(m1.getDoubleAt(10), m2.getDoubleAt(24), fma(m1.getDoubleAt(11), m2.getDoubleAt(28)))));
					temp[9] = fma(m1.getDoubleAt(8)  , m2.getDoubleAt(17), fma(m1.getDoubleAt(9) , m2.getDoubleAt(21), fma(m1.getDoubleAt(10), m2.getDoubleAt(25), fma(m1.getDoubleAt(11), m2.getDoubleAt(29)))));
					temp[10] = fma(m1.getDoubleAt(8) , m2.getDoubleAt(18), fma(m1.getDoubleAt(9) , m2.getDoubleAt(22), fma(m1.getDoubleAt(10), m2.getDoubleAt(26), fma(m1.getDoubleAt(11), m2.getDoubleAt(30)))));
					temp[11] = fma(m1.getDoubleAt(8) , m2.getDoubleAt(19), fma(m1.getDoubleAt(9) , m2.getDoubleAt(23), fma(m1.getDoubleAt(10), m2.getDoubleAt(27), fma(m1.getDoubleAt(11), m2.getDoubleAt(31)))));
					temp[12] = fma(m1.getDoubleAt(12), m2.getDoubleAt(16), fma(m1.getDoubleAt(13), m2.getDoubleAt(20), fma(m1.getDoubleAt(14), m2.getDoubleAt(24), fma(m1.getDoubleAt(15), m2.getDoubleAt(28)))));
					temp[13] = fma(m1.getDoubleAt(12), m2.getDoubleAt(17), fma(m1.getDoubleAt(13), m2.getDoubleAt(21), fma(m1.getDoubleAt(14), m2.getDoubleAt(25), fma(m1.getDoubleAt(15), m2.getDoubleAt(29)))));
					temp[14] = fma(m1.getDoubleAt(12), m2.getDoubleAt(18), fma(m1.getDoubleAt(13), m2.getDoubleAt(22), fma(m1.getDoubleAt(14), m2.getDoubleAt(26), fma(m1.getDoubleAt(15), m2.getDoubleAt(30)))));
					temp[15] = fma(m1.getDoubleAt(12), m2.getDoubleAt(19), fma(m1.getDoubleAt(13), m2.getDoubleAt(23), fma(m1.getDoubleAt(14), m2.getDoubleAt(27), fma(m1.getDoubleAt(15), m2.getDoubleAt(31)))));
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, fma(m1[0]  , m2[0], fma(m1[1] , m2[4], fma(m1[2] , m2[8] , fma(m1[3] , m2[12])))));
				temp.setVectorAt(1, fma(m1[0]  , m2[1], fma(m1[1] , m2[5], fma(m1[2] , m2[9] , fma(m1[3] , m2[13])))));
				temp.setVectorAt(2, fma(m1[0]  , m2[2], fma(m1[1] , m2[6], fma(m1[2] , m2[10], fma(m1[3] , m2[14])))));
				temp.setVectorAt(3, fma(m1[0]  , m2[3], fma(m1[1] , m2[7], fma(m1[2] , m2[11], fma(m1[3] , m2[15])))));
				temp.setVectorAt(4, fma(m1[4]  , m2[0], fma(m1[5] , m2[4], fma(m1[6] , m2[8] , fma(m1[7] , m2[12])))));
				temp.setVectorAt(5, fma(m1[4]  , m2[1], fma(m1[5] , m2[5], fma(m1[6] , m2[9] , fma(m1[7] , m2[13])))));
				temp.setVectorAt(6, fma(m1[4]  , m2[2], fma(m1[5] , m2[6], fma(m1[6] , m2[10], fma(m1[7] , m2[14])))));
				temp.setVectorAt(7, fma(m1[4]  , m2[3], fma(m1[5] , m2[7], fma(m1[6] , m2[11], fma(m1[7] , m2[15])))));
				temp.setVectorAt(8, fma(m1[8]  , m2[0], fma(m1[9] , m2[4], fma(m1[10], m2[8] , fma(m1[11], m2[12])))));
				temp.setVectorAt(9, fma(m1[8]  , m2[1], fma(m1[9] , m2[5], fma(m1[10], m2[9] , fma(m1[11], m2[13])))));
				temp.setVectorAt(10, fma(m1[8] , m2[2], fma(m1[9] , m2[6], fma(m1[10], m2[10], fma(m1[11], m2[14])))));
				temp.setVectorAt(11, fma(m1[8] , m2[3], fma(m1[9] , m2[7], fma(m1[10], m2[11], fma(m1[11], m2[15])))));
				temp.setVectorAt(12, fma(m1[12], m2[0], fma(m1[13], m2[4], fma(m1[14], m2[8] , fma(m1[15], m2[12])))));
				temp.setVectorAt(13, fma(m1[12], m2[1], fma(m1[13], m2[5], fma(m1[14], m2[9] , fma(m1[15], m2[13])))));
				temp.setVectorAt(14, fma(m1[12], m2[2], fma(m1[13], m2[6], fma(m1[14], m2[10], fma(m1[15], m2[14])))));
				temp.setVectorAt(15, fma(m1[12], m2[3], fma(m1[13], m2[7], fma(m1[14], m2[11], fma(m1[15], m2[15])))));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, fma(m1[0]  , m2.getDoubleAt(0), fma(m1[1], m2.getDoubleAt(4) , fma(m1[2] , m2.getDoubleAt(8) , fma(m1[3] , m2.getDoubleAt(12))))));
					temp.setVectorAt(1, fma(m1[0]  , m2.getDoubleAt(1), fma(m1[1], m2.getDoubleAt(5) , fma(m1[2] , m2.getDoubleAt(9) , fma(m1[3] , m2.getDoubleAt(13))))));
					temp.setVectorAt(2, fma(m1[0]  , m2.getDoubleAt(2), fma(m1[1], m2.getDoubleAt(6) , fma(m1[2] , m2.getDoubleAt(10), fma(m1[3] , m2.getDoubleAt(14))))));
					temp.setVectorAt(3, fma(m1[0]  , m2.getDoubleAt(3), fma(m1[1], m2.getDoubleAt(7) , fma(m1[2] , m2.getDoubleAt(11), fma(m1[3] , m2.getDoubleAt(15))))));
					temp.setVectorAt(4, fma(m1[4]  , m2.getDoubleAt(0), fma(m1[5], m2.getDoubleAt(4) , fma(m1[6] , m2.getDoubleAt(8) , fma(m1[7] , m2.getDoubleAt(12))))));
					temp.setVectorAt(5, fma(m1[4]  , m2.getDoubleAt(1), fma(m1[5], m2.getDoubleAt(5) , fma(m1[6] , m2.getDoubleAt(9) , fma(m1[7] , m2.getDoubleAt(13))))));
					temp.setVectorAt(6, fma(m1[4]  , m2.getDoubleAt(2), fma(m1[5], m2.getDoubleAt(6) , fma(m1[6] , m2.getDoubleAt(10), fma(m1[7] , m2.getDoubleAt(14))))));
					temp.setVectorAt(7, fma(m1[4]  , m2.getDoubleAt(3), fma(m1[5], m2.getDoubleAt(7) , fma(m1[6] , m2.getDoubleAt(11), fma(m1[7] , m2.getDoubleAt(15))))));
					temp.setVectorAt(8, fma(m1[8]  , m2.getDoubleAt(0), fma(m1[9], m2.getDoubleAt(4) , fma(m1[10], m2.getDoubleAt(8) , fma(m1[11], m2.getDoubleAt(12))))));
					temp.setVectorAt(9, fma(m1[8]  , m2.getDoubleAt(1), fma(m1[9], m2.getDoubleAt(5) , fma(m1[10], m2.getDoubleAt(9) , fma(m1[11], m2.getDoubleAt(13))))));
					temp.setVectorAt(10, fma(m1[8] , m2.getDoubleAt(2), fma(m1[9], m2.getDoubleAt(6) , fma(m1[10], m2.getDoubleAt(10), fma(m1[11], m2.getDoubleAt(14))))));
					temp.setVectorAt(11, fma(m1[8] , m2.getDoubleAt(3), fma(m1[9], m2.getDoubleAt(7) , fma(m1[10], m2.getDoubleAt(11), fma(m1[11], m2.getDoubleAt(15))))));
					temp.setVectorAt(12, fma(m1[12], m2.getDoubleAt(0), fma(m1[13], m2.getDoubleAt(4), fma(m1[14], m2.getDoubleAt(8) , fma(m1[15], m2.getDoubleAt(12))))));
					temp.setVectorAt(13, fma(m1[12], m2.getDoubleAt(1), fma(m1[13], m2.getDoubleAt(5), fma(m1[14], m2.getDoubleAt(9) , fma(m1[15], m2.getDoubleAt(13))))));
					temp.setVectorAt(14, fma(m1[12], m2.getDoubleAt(2), fma(m1[13], m2.getDoubleAt(6), fma(m1[14], m2.getDoubleAt(10), fma(m1[15], m2.getDoubleAt(14))))));
					temp.setVectorAt(15, fma(m1[12], m2.getDoubleAt(3), fma(m1[13], m2.getDoubleAt(7), fma(m1[14], m2.getDoubleAt(11), fma(m1[15], m2.getDoubleAt(15))))));
				} else {
					temp.setVectorAt(16, fma(m1[0] , m2.getDoubleAt(0), fma(m1[1] , m2.getDoubleAt(4), fma(m1[2] , m2.getDoubleAt(8) , fma(m1[3] , m2.getDoubleAt(12))))));
					temp.setVectorAt(17, fma(m1[0] , m2.getDoubleAt(1), fma(m1[1] , m2.getDoubleAt(5), fma(m1[2] , m2.getDoubleAt(9) , fma(m1[3] , m2.getDoubleAt(13))))));
					temp.setVectorAt(18, fma(m1[0] , m2.getDoubleAt(2), fma(m1[1] , m2.getDoubleAt(6), fma(m1[2] , m2.getDoubleAt(10), fma(m1[3] , m2.getDoubleAt(14))))));
					temp.setVectorAt(19, fma(m1[0] , m2.getDoubleAt(3), fma(m1[1] , m2.getDoubleAt(7), fma(m1[2] , m2.getDoubleAt(11), fma(m1[3] , m2.getDoubleAt(15))))));
					temp.setVectorAt(20, fma(m1[4] , m2.getDoubleAt(0), fma(m1[5] , m2.getDoubleAt(4), fma(m1[6] , m2.getDoubleAt(8) , fma(m1[7] , m2.getDoubleAt(12))))));
					temp.setVectorAt(21, fma(m1[4] , m2.getDoubleAt(1), fma(m1[5] , m2.getDoubleAt(5), fma(m1[6] , m2.getDoubleAt(9) , fma(m1[7] , m2.getDoubleAt(13))))));
					temp.setVectorAt(22, fma(m1[4] , m2.getDoubleAt(2), fma(m1[5] , m2.getDoubleAt(6), fma(m1[6] , m2.getDoubleAt(10), fma(m1[7] , m2.getDoubleAt(14))))));
					temp.setVectorAt(23, fma(m1[4] , m2.getDoubleAt(3), fma(m1[5] , m2.getDoubleAt(7), fma(m1[6] , m2.getDoubleAt(11), fma(m1[7] , m2.getDoubleAt(15))))));
					temp.setVectorAt(24, fma(m1[8] , m2.getDoubleAt(0), fma(m1[9] , m2.getDoubleAt(4), fma(m1[10], m2.getDoubleAt(8) , fma(m1[11], m2.getDoubleAt(12))))));
					temp.setVectorAt(25, fma(m1[8] , m2.getDoubleAt(1), fma(m1[9] , m2.getDoubleAt(5), fma(m1[10], m2.getDoubleAt(9) , fma(m1[11], m2.getDoubleAt(13))))));
					temp.setVectorAt(26, fma(m1[8] , m2.getDoubleAt(2), fma(m1[9] , m2.getDoubleAt(6), fma(m1[10], m2.getDoubleAt(10), fma(m1[11], m2.getDoubleAt(14))))));
					temp.setVectorAt(27, fma(m1[8] , m2.getDoubleAt(3), fma(m1[9] , m2.getDoubleAt(7), fma(m1[10], m2.getDoubleAt(11), fma(m1[11], m2.getDoubleAt(15))))));
					temp.setVectorAt(28, fma(m1[12], m2.getDoubleAt(0), fma(m1[13], m2.getDoubleAt(4), fma(m1[14], m2.getDoubleAt(8) , fma(m1[15], m2.getDoubleAt(12))))));
					temp.setVectorAt(29, fma(m1[12], m2.getDoubleAt(1), fma(m1[13], m2.getDoubleAt(5), fma(m1[14], m2.getDoubleAt(9) , fma(m1[15], m2.getDoubleAt(13))))));
					temp.setVectorAt(30, fma(m1[12], m2.getDoubleAt(2), fma(m1[13], m2.getDoubleAt(6), fma(m1[14], m2.getDoubleAt(10), fma(m1[15], m2.getDoubleAt(14))))));
					temp.setVectorAt(31, fma(m1[12], m2.getDoubleAt(3), fma(m1[13], m2.getDoubleAt(7), fma(m1[14], m2.getDoubleAt(11), fma(m1[15], m2.getDoubleAt(15))))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, fma(m1.getDoubleAt(0)  , m2[0], fma(m1.getDoubleAt(1) , m2[4], fma(m1.getDoubleAt(2) , m2[8] , fma(m1.getDoubleAt(3) , m2[12])))));
					temp.setVectorAt(1, fma(m1.getDoubleAt(0)  , m2[1], fma(m1.getDoubleAt(1) , m2[5], fma(m1.getDoubleAt(2) , m2[9] , fma(m1.getDoubleAt(3) , m2[13])))));
					temp.setVectorAt(2, fma(m1.getDoubleAt(0)  , m2[2], fma(m1.getDoubleAt(1) , m2[6], fma(m1.getDoubleAt(2) , m2[10], fma(m1.getDoubleAt(3) , m2[14])))));
					temp.setVectorAt(3, fma(m1.getDoubleAt(0)  , m2[3], fma(m1.getDoubleAt(1) , m2[7], fma(m1.getDoubleAt(2) , m2[11], fma(m1.getDoubleAt(3) , m2[15])))));
					temp.setVectorAt(4, fma(m1.getDoubleAt(4)  , m2[0], fma(m1.getDoubleAt(5) , m2[4], fma(m1.getDoubleAt(6) , m2[8] , fma(m1.getDoubleAt(7) , m2[12])))));
					temp.setVectorAt(5, fma(m1.getDoubleAt(4)  , m2[1], fma(m1.getDoubleAt(5) , m2[5], fma(m1.getDoubleAt(6) , m2[9] , fma(m1.getDoubleAt(7) , m2[13])))));
					temp.setVectorAt(6, fma(m1.getDoubleAt(4)  , m2[2], fma(m1.getDoubleAt(5) , m2[6], fma(m1.getDoubleAt(6) , m2[10], fma(m1.getDoubleAt(7) , m2[14])))));
					temp.setVectorAt(7, fma(m1.getDoubleAt(4)  , m2[3], fma(m1.getDoubleAt(5) , m2[7], fma(m1.getDoubleAt(6) , m2[11], fma(m1.getDoubleAt(7) , m2[15])))));
					temp.setVectorAt(8, fma(m1.getDoubleAt(8)  , m2[0], fma(m1.getDoubleAt(9) , m2[4], fma(m1.getDoubleAt(10), m2[8] , fma(m1.getDoubleAt(11), m2[12])))));
					temp.setVectorAt(9, fma(m1.getDoubleAt(8)  , m2[1], fma(m1.getDoubleAt(9) , m2[5], fma(m1.getDoubleAt(10), m2[9] , fma(m1.getDoubleAt(11), m2[13])))));
					temp.setVectorAt(10, fma(m1.getDoubleAt(8) , m2[2], fma(m1.getDoubleAt(9) , m2[6], fma(m1.getDoubleAt(10), m2[10], fma(m1.getDoubleAt(11), m2[14])))));
					temp.setVectorAt(11, fma(m1.getDoubleAt(8) , m2[3], fma(m1.getDoubleAt(9) , m2[7], fma(m1.getDoubleAt(10), m2[11], fma(m1.getDoubleAt(11), m2[15])))));
					temp.setVectorAt(12, fma(m1.getDoubleAt(12), m2[0], fma(m1.getDoubleAt(13), m2[4], fma(m1.getDoubleAt(14), m2[8] , fma(m1.getDoubleAt(15), m2[12])))));
					temp.setVectorAt(13, fma(m1.getDoubleAt(12), m2[1], fma(m1.getDoubleAt(13), m2[5], fma(m1.getDoubleAt(14), m2[9] , fma(m1.getDoubleAt(15), m2[13])))));
					temp.setVectorAt(14, fma(m1.getDoubleAt(12), m2[2], fma(m1.getDoubleAt(13), m2[6], fma(m1.getDoubleAt(14), m2[10], fma(m1.getDoubleAt(15), m2[14])))));
					temp.setVectorAt(15, fma(m1.getDoubleAt(12), m2[3], fma(m1.getDoubleAt(13), m2[7], fma(m1.getDoubleAt(14), m2[11], fma(m1.getDoubleAt(15), m2[15])))));
				} else {
					temp.setVectorAt(16, fma(m1.getDoubleAt(0) , m2[0], fma(m1.getDoubleAt(1) , m2[4], fma(m1.getDoubleAt(2) , m2[8] , fma(m1.getDoubleAt(3) , m2[12])))));
					temp.setVectorAt(17, fma(m1.getDoubleAt(0) , m2[1], fma(m1.getDoubleAt(1) , m2[5], fma(m1.getDoubleAt(2) , m2[9] , fma(m1.getDoubleAt(3) , m2[13])))));
					temp.setVectorAt(18, fma(m1.getDoubleAt(0) , m2[2], fma(m1.getDoubleAt(1) , m2[6], fma(m1.getDoubleAt(2) , m2[10], fma(m1.getDoubleAt(3) , m2[14])))));
					temp.setVectorAt(19, fma(m1.getDoubleAt(0) , m2[3], fma(m1.getDoubleAt(1) , m2[7], fma(m1.getDoubleAt(2) , m2[11], fma(m1.getDoubleAt(3) , m2[15])))));
					temp.setVectorAt(20, fma(m1.getDoubleAt(4) , m2[0], fma(m1.getDoubleAt(5) , m2[4], fma(m1.getDoubleAt(6) , m2[8] , fma(m1.getDoubleAt(7) , m2[12])))));
					temp.setVectorAt(21, fma(m1.getDoubleAt(4) , m2[1], fma(m1.getDoubleAt(5) , m2[5], fma(m1.getDoubleAt(6) , m2[9] , fma(m1.getDoubleAt(7) , m2[13])))));
					temp.setVectorAt(22, fma(m1.getDoubleAt(4) , m2[2], fma(m1.getDoubleAt(5) , m2[6], fma(m1.getDoubleAt(6) , m2[10], fma(m1.getDoubleAt(7) , m2[14])))));
					temp.setVectorAt(23, fma(m1.getDoubleAt(4) , m2[3], fma(m1.getDoubleAt(5) , m2[7], fma(m1.getDoubleAt(6) , m2[11], fma(m1.getDoubleAt(7) , m2[15])))));
					temp.setVectorAt(24, fma(m1.getDoubleAt(8) , m2[0], fma(m1.getDoubleAt(9) , m2[4], fma(m1.getDoubleAt(10), m2[8] , fma(m1.getDoubleAt(11), m2[12])))));
					temp.setVectorAt(25, fma(m1.getDoubleAt(8) , m2[1], fma(m1.getDoubleAt(9) , m2[5], fma(m1.getDoubleAt(10), m2[9] , fma(m1.getDoubleAt(11), m2[13])))));
					temp.setVectorAt(26, fma(m1.getDoubleAt(8) , m2[2], fma(m1.getDoubleAt(9) , m2[6], fma(m1.getDoubleAt(10), m2[10], fma(m1.getDoubleAt(11), m2[14])))));
					temp.setVectorAt(27, fma(m1.getDoubleAt(8) , m2[3], fma(m1.getDoubleAt(9) , m2[7], fma(m1.getDoubleAt(10), m2[11], fma(m1.getDoubleAt(11), m2[15])))));
					temp.setVectorAt(28, fma(m1.getDoubleAt(12), m2[0], fma(m1.getDoubleAt(13), m2[4], fma(m1.getDoubleAt(14), m2[8] , fma(m1.getDoubleAt(15), m2[12])))));
					temp.setVectorAt(29, fma(m1.getDoubleAt(12), m2[1], fma(m1.getDoubleAt(13), m2[5], fma(m1.getDoubleAt(14), m2[9] , fma(m1.getDoubleAt(15), m2[13])))));
					temp.setVectorAt(30, fma(m1.getDoubleAt(12), m2[2], fma(m1.getDoubleAt(13), m2[6], fma(m1.getDoubleAt(14), m2[10], fma(m1.getDoubleAt(15), m2[14])))));
					temp.setVectorAt(31, fma(m1.getDoubleAt(12), m2[3], fma(m1.getDoubleAt(13), m2[7], fma(m1.getDoubleAt(14), m2[11], fma(m1.getDoubleAt(15), m2[15])))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(4), fma(m1.getDoubleAt(2) , m2.getDoubleAt(8) , fma(m1.getDoubleAt(3) , m2.getDoubleAt(12))))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(1), fma(m1.getDoubleAt(1) , m2.getDoubleAt(5), fma(m1.getDoubleAt(2) , m2.getDoubleAt(9) , fma(m1.getDoubleAt(3) , m2.getDoubleAt(13))))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(2), fma(m1.getDoubleAt(1) , m2.getDoubleAt(6), fma(m1.getDoubleAt(2) , m2.getDoubleAt(10), fma(m1.getDoubleAt(3) , m2.getDoubleAt(14))))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(3), fma(m1.getDoubleAt(1) , m2.getDoubleAt(7), fma(m1.getDoubleAt(2) , m2.getDoubleAt(11), fma(m1.getDoubleAt(3) , m2.getDoubleAt(15))))));
						temp.setVectorAt(4, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(4), fma(m1.getDoubleAt(6) , m2.getDoubleAt(8) , fma(m1.getDoubleAt(7) , m2.getDoubleAt(12))))));
						temp.setVectorAt(5, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(1), fma(m1.getDoubleAt(5) , m2.getDoubleAt(5), fma(m1.getDoubleAt(6) , m2.getDoubleAt(9) , fma(m1.getDoubleAt(7) , m2.getDoubleAt(13))))));
						temp.setVectorAt(6, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(2), fma(m1.getDoubleAt(5) , m2.getDoubleAt(6), fma(m1.getDoubleAt(6) , m2.getDoubleAt(10), fma(m1.getDoubleAt(7) , m2.getDoubleAt(14))))));
						temp.setVectorAt(7, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(3), fma(m1.getDoubleAt(5) , m2.getDoubleAt(7), fma(m1.getDoubleAt(6) , m2.getDoubleAt(11), fma(m1.getDoubleAt(7) , m2.getDoubleAt(15))))));
						temp.setVectorAt(8, fma(m1.getDoubleAt(8)  , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(4), fma(m1.getDoubleAt(10), m2.getDoubleAt(8) , fma(m1.getDoubleAt(11), m2.getDoubleAt(12))))));
						temp.setVectorAt(9, fma(m1.getDoubleAt(8)  , m2.getDoubleAt(1), fma(m1.getDoubleAt(9) , m2.getDoubleAt(5), fma(m1.getDoubleAt(10), m2.getDoubleAt(9) , fma(m1.getDoubleAt(11), m2.getDoubleAt(13))))));
						temp.setVectorAt(10, fma(m1.getDoubleAt(8) , m2.getDoubleAt(2), fma(m1.getDoubleAt(9) , m2.getDoubleAt(6), fma(m1.getDoubleAt(10), m2.getDoubleAt(10), fma(m1.getDoubleAt(11), m2.getDoubleAt(14))))));
						temp.setVectorAt(11, fma(m1.getDoubleAt(8) , m2.getDoubleAt(3), fma(m1.getDoubleAt(9) , m2.getDoubleAt(7), fma(m1.getDoubleAt(10), m2.getDoubleAt(11), fma(m1.getDoubleAt(11), m2.getDoubleAt(15))))));
						temp.setVectorAt(12, fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(4), fma(m1.getDoubleAt(14), m2.getDoubleAt(8) , fma(m1.getDoubleAt(15), m2.getDoubleAt(12))))));
						temp.setVectorAt(13, fma(m1.getDoubleAt(12), m2.getDoubleAt(1), fma(m1.getDoubleAt(13), m2.getDoubleAt(5), fma(m1.getDoubleAt(14), m2.getDoubleAt(9) , fma(m1.getDoubleAt(15), m2.getDoubleAt(13))))));
						temp.setVectorAt(14, fma(m1.getDoubleAt(12), m2.getDoubleAt(2), fma(m1.getDoubleAt(13), m2.getDoubleAt(6), fma(m1.getDoubleAt(14), m2.getDoubleAt(10), fma(m1.getDoubleAt(15), m2.getDoubleAt(14))))));
						temp.setVectorAt(15, fma(m1.getDoubleAt(12), m2.getDoubleAt(3), fma(m1.getDoubleAt(13), m2.getDoubleAt(7), fma(m1.getDoubleAt(14), m2.getDoubleAt(11), fma(m1.getDoubleAt(15), m2.getDoubleAt(15))))));
					} else {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(16), fma(m1.getDoubleAt(1) , m2.getDoubleAt(20), fma(m1.getDoubleAt(2) , m2.getDoubleAt(24), fma(m1.getDoubleAt(3) , m2.getDoubleAt(28))))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(17), fma(m1.getDoubleAt(1) , m2.getDoubleAt(21), fma(m1.getDoubleAt(2) , m2.getDoubleAt(25), fma(m1.getDoubleAt(3) , m2.getDoubleAt(29))))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(18), fma(m1.getDoubleAt(1) , m2.getDoubleAt(22), fma(m1.getDoubleAt(2) , m2.getDoubleAt(26), fma(m1.getDoubleAt(3) , m2.getDoubleAt(30))))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(0)  , m2.getDoubleAt(19), fma(m1.getDoubleAt(1) , m2.getDoubleAt(23), fma(m1.getDoubleAt(2) , m2.getDoubleAt(27), fma(m1.getDoubleAt(3) , m2.getDoubleAt(31))))));
						temp.setVectorAt(4, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(16), fma(m1.getDoubleAt(5) , m2.getDoubleAt(20), fma(m1.getDoubleAt(6) , m2.getDoubleAt(24), fma(m1.getDoubleAt(7) , m2.getDoubleAt(28))))));
						temp.setVectorAt(5, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(17), fma(m1.getDoubleAt(5) , m2.getDoubleAt(21), fma(m1.getDoubleAt(6) , m2.getDoubleAt(25), fma(m1.getDoubleAt(7) , m2.getDoubleAt(29))))));
						temp.setVectorAt(6, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(18), fma(m1.getDoubleAt(5) , m2.getDoubleAt(22), fma(m1.getDoubleAt(6) , m2.getDoubleAt(26), fma(m1.getDoubleAt(7) , m2.getDoubleAt(30))))));
						temp.setVectorAt(7, fma(m1.getDoubleAt(4)  , m2.getDoubleAt(19), fma(m1.getDoubleAt(5) , m2.getDoubleAt(23), fma(m1.getDoubleAt(6) , m2.getDoubleAt(27), fma(m1.getDoubleAt(7) , m2.getDoubleAt(31))))));
						temp.setVectorAt(8, fma(m1.getDoubleAt(8)  , m2.getDoubleAt(16), fma(m1.getDoubleAt(9) , m2.getDoubleAt(20), fma(m1.getDoubleAt(10), m2.getDoubleAt(24), fma(m1.getDoubleAt(11), m2.getDoubleAt(28))))));
						temp.setVectorAt(9, fma(m1.getDoubleAt(8)  , m2.getDoubleAt(17), fma(m1.getDoubleAt(9) , m2.getDoubleAt(21), fma(m1.getDoubleAt(10), m2.getDoubleAt(25), fma(m1.getDoubleAt(11), m2.getDoubleAt(29))))));
						temp.setVectorAt(10, fma(m1.getDoubleAt(8) , m2.getDoubleAt(18), fma(m1.getDoubleAt(9) , m2.getDoubleAt(22), fma(m1.getDoubleAt(10), m2.getDoubleAt(26), fma(m1.getDoubleAt(11), m2.getDoubleAt(30))))));
						temp.setVectorAt(11, fma(m1.getDoubleAt(8) , m2.getDoubleAt(19), fma(m1.getDoubleAt(9) , m2.getDoubleAt(23), fma(m1.getDoubleAt(10), m2.getDoubleAt(27), fma(m1.getDoubleAt(11), m2.getDoubleAt(31))))));
						temp.setVectorAt(12, fma(m1.getDoubleAt(12), m2.getDoubleAt(16), fma(m1.getDoubleAt(13), m2.getDoubleAt(20), fma(m1.getDoubleAt(14), m2.getDoubleAt(24), fma(m1.getDoubleAt(15), m2.getDoubleAt(28))))));
						temp.setVectorAt(13, fma(m1.getDoubleAt(12), m2.getDoubleAt(17), fma(m1.getDoubleAt(13), m2.getDoubleAt(21), fma(m1.getDoubleAt(14), m2.getDoubleAt(25), fma(m1.getDoubleAt(15), m2.getDoubleAt(29))))));
						temp.setVectorAt(14, fma(m1.getDoubleAt(12), m2.getDoubleAt(18), fma(m1.getDoubleAt(13), m2.getDoubleAt(22), fma(m1.getDoubleAt(14), m2.getDoubleAt(26), fma(m1.getDoubleAt(15), m2.getDoubleAt(30))))));
						temp.setVectorAt(15, fma(m1.getDoubleAt(12), m2.getDoubleAt(19), fma(m1.getDoubleAt(13), m2.getDoubleAt(23), fma(m1.getDoubleAt(14), m2.getDoubleAt(27), fma(m1.getDoubleAt(15), m2.getDoubleAt(31))))));
					}
				} else if(m2 != temp || m1 != temp) {
					temp.setVectorAt(16, fma(m1.getDoubleAt(0) , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(4), fma(m1.getDoubleAt(2) , m2.getDoubleAt(8) , fma(m1.getDoubleAt(3) , m2.getDoubleAt(12))))));
					temp.setVectorAt(17, fma(m1.getDoubleAt(0) , m2.getDoubleAt(1), fma(m1.getDoubleAt(1) , m2.getDoubleAt(5), fma(m1.getDoubleAt(2) , m2.getDoubleAt(9) , fma(m1.getDoubleAt(3) , m2.getDoubleAt(13))))));
					temp.setVectorAt(18, fma(m1.getDoubleAt(0) , m2.getDoubleAt(2), fma(m1.getDoubleAt(1) , m2.getDoubleAt(6), fma(m1.getDoubleAt(2) , m2.getDoubleAt(10), fma(m1.getDoubleAt(3) , m2.getDoubleAt(14))))));
					temp.setVectorAt(19, fma(m1.getDoubleAt(0) , m2.getDoubleAt(3), fma(m1.getDoubleAt(1) , m2.getDoubleAt(7), fma(m1.getDoubleAt(2) , m2.getDoubleAt(11), fma(m1.getDoubleAt(3) , m2.getDoubleAt(15))))));
					temp.setVectorAt(20, fma(m1.getDoubleAt(4) , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(4), fma(m1.getDoubleAt(6) , m2.getDoubleAt(8) , fma(m1.getDoubleAt(7) , m2.getDoubleAt(12))))));
					temp.setVectorAt(21, fma(m1.getDoubleAt(4) , m2.getDoubleAt(1), fma(m1.getDoubleAt(5) , m2.getDoubleAt(5), fma(m1.getDoubleAt(6) , m2.getDoubleAt(9) , fma(m1.getDoubleAt(7) , m2.getDoubleAt(13))))));
					temp.setVectorAt(22, fma(m1.getDoubleAt(4) , m2.getDoubleAt(2), fma(m1.getDoubleAt(5) , m2.getDoubleAt(6), fma(m1.getDoubleAt(6) , m2.getDoubleAt(10), fma(m1.getDoubleAt(7) , m2.getDoubleAt(14))))));
					temp.setVectorAt(23, fma(m1.getDoubleAt(4) , m2.getDoubleAt(3), fma(m1.getDoubleAt(5) , m2.getDoubleAt(7), fma(m1.getDoubleAt(6) , m2.getDoubleAt(11), fma(m1.getDoubleAt(7) , m2.getDoubleAt(15))))));
					temp.setVectorAt(24, fma(m1.getDoubleAt(8) , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(4), fma(m1.getDoubleAt(10), m2.getDoubleAt(8) , fma(m1.getDoubleAt(11), m2.getDoubleAt(12))))));
					temp.setVectorAt(25, fma(m1.getDoubleAt(8) , m2.getDoubleAt(1), fma(m1.getDoubleAt(9) , m2.getDoubleAt(5), fma(m1.getDoubleAt(10), m2.getDoubleAt(9) , fma(m1.getDoubleAt(11), m2.getDoubleAt(13))))));
					temp.setVectorAt(26, fma(m1.getDoubleAt(8) , m2.getDoubleAt(2), fma(m1.getDoubleAt(9) , m2.getDoubleAt(6), fma(m1.getDoubleAt(10), m2.getDoubleAt(10), fma(m1.getDoubleAt(11), m2.getDoubleAt(14))))));
					temp.setVectorAt(27, fma(m1.getDoubleAt(8) , m2.getDoubleAt(3), fma(m1.getDoubleAt(9) , m2.getDoubleAt(7), fma(m1.getDoubleAt(10), m2.getDoubleAt(11), fma(m1.getDoubleAt(11), m2.getDoubleAt(15))))));
					temp.setVectorAt(28, fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(4), fma(m1.getDoubleAt(14), m2.getDoubleAt(8) , fma(m1.getDoubleAt(15), m2.getDoubleAt(12))))));
					temp.setVectorAt(29, fma(m1.getDoubleAt(12), m2.getDoubleAt(1), fma(m1.getDoubleAt(13), m2.getDoubleAt(5), fma(m1.getDoubleAt(14), m2.getDoubleAt(9) , fma(m1.getDoubleAt(15), m2.getDoubleAt(13))))));
					temp.setVectorAt(30, fma(m1.getDoubleAt(12), m2.getDoubleAt(2), fma(m1.getDoubleAt(13), m2.getDoubleAt(6), fma(m1.getDoubleAt(14), m2.getDoubleAt(10), fma(m1.getDoubleAt(15), m2.getDoubleAt(14))))));
					temp.setVectorAt(31, fma(m1.getDoubleAt(12), m2.getDoubleAt(3), fma(m1.getDoubleAt(13), m2.getDoubleAt(7), fma(m1.getDoubleAt(14), m2.getDoubleAt(11), fma(m1.getDoubleAt(15), m2.getDoubleAt(15))))));
				} else {
					temp.setVectorAt(32, fma(m1.getDoubleAt(0) , m2.getDoubleAt(16), fma(m1.getDoubleAt(1) , m2.getDoubleAt(20), fma(m1.getDoubleAt(2) , m2.getDoubleAt(24), fma(m1.getDoubleAt(3) , m2.getDoubleAt(28))))));
					temp.setVectorAt(33, fma(m1.getDoubleAt(0) , m2.getDoubleAt(17), fma(m1.getDoubleAt(1) , m2.getDoubleAt(21), fma(m1.getDoubleAt(2) , m2.getDoubleAt(25), fma(m1.getDoubleAt(3) , m2.getDoubleAt(29))))));
					temp.setVectorAt(34, fma(m1.getDoubleAt(0) , m2.getDoubleAt(18), fma(m1.getDoubleAt(1) , m2.getDoubleAt(22), fma(m1.getDoubleAt(2) , m2.getDoubleAt(26), fma(m1.getDoubleAt(3) , m2.getDoubleAt(30))))));
					temp.setVectorAt(35, fma(m1.getDoubleAt(0) , m2.getDoubleAt(19), fma(m1.getDoubleAt(1) , m2.getDoubleAt(23), fma(m1.getDoubleAt(2) , m2.getDoubleAt(27), fma(m1.getDoubleAt(3) , m2.getDoubleAt(31))))));
					temp.setVectorAt(36, fma(m1.getDoubleAt(4) , m2.getDoubleAt(16), fma(m1.getDoubleAt(5) , m2.getDoubleAt(20), fma(m1.getDoubleAt(6) , m2.getDoubleAt(24), fma(m1.getDoubleAt(7) , m2.getDoubleAt(28))))));
					temp.setVectorAt(37, fma(m1.getDoubleAt(4) , m2.getDoubleAt(17), fma(m1.getDoubleAt(5) , m2.getDoubleAt(21), fma(m1.getDoubleAt(6) , m2.getDoubleAt(25), fma(m1.getDoubleAt(7) , m2.getDoubleAt(29))))));
					temp.setVectorAt(38, fma(m1.getDoubleAt(4) , m2.getDoubleAt(18), fma(m1.getDoubleAt(5) , m2.getDoubleAt(22), fma(m1.getDoubleAt(6) , m2.getDoubleAt(26), fma(m1.getDoubleAt(7) , m2.getDoubleAt(30))))));
					temp.setVectorAt(39, fma(m1.getDoubleAt(4) , m2.getDoubleAt(19), fma(m1.getDoubleAt(5) , m2.getDoubleAt(23), fma(m1.getDoubleAt(6) , m2.getDoubleAt(27), fma(m1.getDoubleAt(7) , m2.getDoubleAt(31))))));
					temp.setVectorAt(40, fma(m1.getDoubleAt(8) , m2.getDoubleAt(16), fma(m1.getDoubleAt(9) , m2.getDoubleAt(20), fma(m1.getDoubleAt(10), m2.getDoubleAt(24), fma(m1.getDoubleAt(11), m2.getDoubleAt(28))))));
					temp.setVectorAt(41, fma(m1.getDoubleAt(8) , m2.getDoubleAt(17), fma(m1.getDoubleAt(9) , m2.getDoubleAt(21), fma(m1.getDoubleAt(10), m2.getDoubleAt(25), fma(m1.getDoubleAt(11), m2.getDoubleAt(29))))));
					temp.setVectorAt(42, fma(m1.getDoubleAt(8) , m2.getDoubleAt(18), fma(m1.getDoubleAt(9) , m2.getDoubleAt(22), fma(m1.getDoubleAt(10), m2.getDoubleAt(26), fma(m1.getDoubleAt(11), m2.getDoubleAt(30))))));
					temp.setVectorAt(43, fma(m1.getDoubleAt(8) , m2.getDoubleAt(19), fma(m1.getDoubleAt(9) , m2.getDoubleAt(23), fma(m1.getDoubleAt(10), m2.getDoubleAt(27), fma(m1.getDoubleAt(11), m2.getDoubleAt(31))))));
					temp.setVectorAt(44, fma(m1.getDoubleAt(12), m2.getDoubleAt(16), fma(m1.getDoubleAt(13), m2.getDoubleAt(20), fma(m1.getDoubleAt(14), m2.getDoubleAt(24), fma(m1.getDoubleAt(15), m2.getDoubleAt(28))))));
					temp.setVectorAt(45, fma(m1.getDoubleAt(12), m2.getDoubleAt(17), fma(m1.getDoubleAt(13), m2.getDoubleAt(21), fma(m1.getDoubleAt(14), m2.getDoubleAt(25), fma(m1.getDoubleAt(15), m2.getDoubleAt(29))))));
					temp.setVectorAt(46, fma(m1.getDoubleAt(12), m2.getDoubleAt(18), fma(m1.getDoubleAt(13), m2.getDoubleAt(22), fma(m1.getDoubleAt(14), m2.getDoubleAt(26), fma(m1.getDoubleAt(15), m2.getDoubleAt(30))))));
					temp.setVectorAt(47, fma(m1.getDoubleAt(12), m2.getDoubleAt(19), fma(m1.getDoubleAt(13), m2.getDoubleAt(23), fma(m1.getDoubleAt(14), m2.getDoubleAt(27), fma(m1.getDoubleAt(15), m2.getDoubleAt(31))))));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	protected static Object multiplyByMatrix_4x4_4x1_FMA(Object _m1, Object _m2, TempVec _temp) {
		final int mM1 = 4; final int nM1 = 4; final int mM2 = 4; final int nM2 = 1;
		{
			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
			if(_m1 == _m2) throw new Error();
			if(!(_m1 instanceof double[]) && !(_m1 instanceof TempVec)) throw new Error();
			if(!(_m2 instanceof double[]) && !(_m2 instanceof TempVec)) throw new Error();
			if(nM1 != mM2) throw new Error();
			if(_temp != null) {
				int expecetedSize = mM1 * nM2;
				if(_m1 == _temp) expecetedSize += mM1 * nM1;
				if(_m2 == _temp) expecetedSize += mM2 * nM2;
				if(((TempVec) _temp).size < expecetedSize) throw new Error();
			}
		}
		if(_temp == null) {
			double[] temp = new double[mM1 * nM2];
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1[0] , m2[0], fma(m1[1] , m2[1], fma(m1[2] , m2[2], fma(m1[3] , m2[3]))));
				temp[1] = fma(m1[4] , m2[0], fma(m1[5] , m2[1], fma(m1[6] , m2[2], fma(m1[7] , m2[3]))));
				temp[2] = fma(m1[8] , m2[0], fma(m1[9] , m2[1], fma(m1[10], m2[2], fma(m1[11], m2[3]))));
				temp[3] = fma(m1[12], m2[0], fma(m1[13], m2[1], fma(m1[14], m2[2], fma(m1[15], m2[3]))));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				temp[0] = fma(m1[0] , m2.getDoubleAt(0), fma(m1[1] , m2.getDoubleAt(1), fma(m1[2] , m2.getDoubleAt(2), fma(m1[3] , m2.getDoubleAt(3)))));
				temp[1] = fma(m1[4] , m2.getDoubleAt(0), fma(m1[5] , m2.getDoubleAt(1), fma(m1[6] , m2.getDoubleAt(2), fma(m1[7] , m2.getDoubleAt(3)))));
				temp[2] = fma(m1[8] , m2.getDoubleAt(0), fma(m1[9] , m2.getDoubleAt(1), fma(m1[10], m2.getDoubleAt(2), fma(m1[11], m2.getDoubleAt(3)))));
				temp[3] = fma(m1[12], m2.getDoubleAt(0), fma(m1[13], m2.getDoubleAt(1), fma(m1[14], m2.getDoubleAt(2), fma(m1[15], m2.getDoubleAt(3)))));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				temp[0] = fma(m1.getDoubleAt(0) , m2[0], fma(m1.getDoubleAt(1) , m2[1], fma(m1.getDoubleAt(2) , m2[2], fma(m1.getDoubleAt(3) , m2[3]))));
				temp[1] = fma(m1.getDoubleAt(4) , m2[0], fma(m1.getDoubleAt(5) , m2[1], fma(m1.getDoubleAt(6) , m2[2], fma(m1.getDoubleAt(7) , m2[3]))));
				temp[2] = fma(m1.getDoubleAt(8) , m2[0], fma(m1.getDoubleAt(9) , m2[1], fma(m1.getDoubleAt(10), m2[2], fma(m1.getDoubleAt(11), m2[3]))));
				temp[3] = fma(m1.getDoubleAt(12), m2[0], fma(m1.getDoubleAt(13), m2[1], fma(m1.getDoubleAt(14), m2[2], fma(m1.getDoubleAt(15), m2[3]))));
				return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != m2) {
					temp[0] = fma(m1.getDoubleAt(0) , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(1), fma(m1.getDoubleAt(2) , m2.getDoubleAt(2), fma(m1.getDoubleAt(3) , m2.getDoubleAt(3)))));
					temp[1] = fma(m1.getDoubleAt(4) , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(1), fma(m1.getDoubleAt(6) , m2.getDoubleAt(2), fma(m1.getDoubleAt(7) , m2.getDoubleAt(3)))));
					temp[2] = fma(m1.getDoubleAt(8) , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(1), fma(m1.getDoubleAt(10), m2.getDoubleAt(2), fma(m1.getDoubleAt(11), m2.getDoubleAt(3)))));
					temp[3] = fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(1), fma(m1.getDoubleAt(14), m2.getDoubleAt(2), fma(m1.getDoubleAt(15), m2.getDoubleAt(3)))));
				} else {
					temp[0] = fma(m1.getDoubleAt(0) , m2.getDoubleAt(16), fma(m1.getDoubleAt(1) , m2.getDoubleAt(17), fma(m1.getDoubleAt(2) , m2.getDoubleAt(18), fma(m1.getDoubleAt(3) , m2.getDoubleAt(19)))));
					temp[1] = fma(m1.getDoubleAt(4) , m2.getDoubleAt(16), fma(m1.getDoubleAt(5) , m2.getDoubleAt(17), fma(m1.getDoubleAt(6) , m2.getDoubleAt(18), fma(m1.getDoubleAt(7) , m2.getDoubleAt(19)))));
					temp[2] = fma(m1.getDoubleAt(8) , m2.getDoubleAt(16), fma(m1.getDoubleAt(9) , m2.getDoubleAt(17), fma(m1.getDoubleAt(10), m2.getDoubleAt(18), fma(m1.getDoubleAt(11), m2.getDoubleAt(19)))));
					temp[3] = fma(m1.getDoubleAt(12), m2.getDoubleAt(16), fma(m1.getDoubleAt(13), m2.getDoubleAt(17), fma(m1.getDoubleAt(14), m2.getDoubleAt(18), fma(m1.getDoubleAt(15), m2.getDoubleAt(19)))));
				} return temp;
			} throw new IllegalStateException();
		} else {
			TempVec temp = _temp;
			if(_m1 instanceof double[] && _m2 instanceof double[]) {
				double[] m1 = (double[]) _m1;
				double[] m2 = (double[]) _m2;
				temp.setVectorAt(0, fma(m1[0] , m2[0], fma(m1[1] , m2[1], fma(m1[2] , m2[2], fma(m1[3] , m2[3])))));
				temp.setVectorAt(1, fma(m1[4] , m2[0], fma(m1[5] , m2[1], fma(m1[6] , m2[2], fma(m1[7] , m2[3])))));
				temp.setVectorAt(2, fma(m1[8] , m2[0], fma(m1[9] , m2[1], fma(m1[10], m2[2], fma(m1[11], m2[3])))));
				temp.setVectorAt(3, fma(m1[12], m2[0], fma(m1[13], m2[1], fma(m1[14], m2[2], fma(m1[15], m2[3])))));
				return temp;
			}
			if(_m1 instanceof double[] && _m2 instanceof TempVec) {
				double[] m1 = (double[]) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m2 != temp) {
					temp.setVectorAt(0, fma(m1[0] , m2.getDoubleAt(0), fma(m1[1] , m2.getDoubleAt(1), fma(m1[2] , m2.getDoubleAt(2), fma(m1[3] , m2.getDoubleAt(3))))));
					temp.setVectorAt(1, fma(m1[4] , m2.getDoubleAt(0), fma(m1[5] , m2.getDoubleAt(1), fma(m1[6] , m2.getDoubleAt(2), fma(m1[7] , m2.getDoubleAt(3))))));
					temp.setVectorAt(2, fma(m1[8] , m2.getDoubleAt(0), fma(m1[9] , m2.getDoubleAt(1), fma(m1[10], m2.getDoubleAt(2), fma(m1[11], m2.getDoubleAt(3))))));
					temp.setVectorAt(3, fma(m1[12], m2.getDoubleAt(0), fma(m1[13], m2.getDoubleAt(1), fma(m1[14], m2.getDoubleAt(2), fma(m1[15], m2.getDoubleAt(3))))));
				} else {
					temp.setVectorAt(4, fma(m1[0] , m2.getDoubleAt(0), fma(m1[1] , m2.getDoubleAt(1), fma(m1[2] , m2.getDoubleAt(2), fma(m1[3] , m2.getDoubleAt(3))))));
					temp.setVectorAt(5, fma(m1[4] , m2.getDoubleAt(0), fma(m1[5] , m2.getDoubleAt(1), fma(m1[6] , m2.getDoubleAt(2), fma(m1[7] , m2.getDoubleAt(3))))));
					temp.setVectorAt(6, fma(m1[8] , m2.getDoubleAt(0), fma(m1[9] , m2.getDoubleAt(1), fma(m1[10], m2.getDoubleAt(2), fma(m1[11], m2.getDoubleAt(3))))));
					temp.setVectorAt(7, fma(m1[12], m2.getDoubleAt(0), fma(m1[13], m2.getDoubleAt(1), fma(m1[14], m2.getDoubleAt(2), fma(m1[15], m2.getDoubleAt(3))))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof double[]) {
				TempVec m1 = (TempVec) _m1;
				double[] m2 = (double[]) _m2;
				if(m1 != temp) {
					temp.setVectorAt(0, fma(m1.getDoubleAt(0) , m2[0], fma(m1.getDoubleAt(1) , m2[1], fma(m1.getDoubleAt(2) , m2[2], fma(m1.getDoubleAt(3) , m2[3])))));
					temp.setVectorAt(1, fma(m1.getDoubleAt(4) , m2[0], fma(m1.getDoubleAt(5) , m2[1], fma(m1.getDoubleAt(6) , m2[2], fma(m1.getDoubleAt(7) , m2[3])))));
					temp.setVectorAt(2, fma(m1.getDoubleAt(8) , m2[0], fma(m1.getDoubleAt(9) , m2[1], fma(m1.getDoubleAt(10), m2[2], fma(m1.getDoubleAt(11), m2[3])))));
					temp.setVectorAt(3, fma(m1.getDoubleAt(12), m2[0], fma(m1.getDoubleAt(13), m2[1], fma(m1.getDoubleAt(14), m2[2], fma(m1.getDoubleAt(15), m2[3])))));
				} else {
					temp.setVectorAt(16, fma(m1.getDoubleAt(0) , m2[0], fma(m1.getDoubleAt(1) , m2[1], fma(m1.getDoubleAt(2) , m2[2], fma(m1.getDoubleAt(3) , m2[3])))));
					temp.setVectorAt(17, fma(m1.getDoubleAt(4) , m2[0], fma(m1.getDoubleAt(5) , m2[1], fma(m1.getDoubleAt(6) , m2[2], fma(m1.getDoubleAt(7) , m2[3])))));
					temp.setVectorAt(18, fma(m1.getDoubleAt(8) , m2[0], fma(m1.getDoubleAt(9) , m2[1], fma(m1.getDoubleAt(10), m2[2], fma(m1.getDoubleAt(11), m2[3])))));
					temp.setVectorAt(19, fma(m1.getDoubleAt(12), m2[0], fma(m1.getDoubleAt(13), m2[1], fma(m1.getDoubleAt(14), m2[2], fma(m1.getDoubleAt(15), m2[3])))));
				} return temp;
			}
			if(_m1 instanceof TempVec && _m2 instanceof TempVec) {
				TempVec m1 = (TempVec) _m1;
				TempVec m2 = (TempVec) _m2;
				if(m1 != temp && m2 != temp) {
					if(m1 != m2) {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0) , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(1), fma(m1.getDoubleAt(2) , m2.getDoubleAt(2), fma(m1.getDoubleAt(3) , m2.getDoubleAt(3))))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(4) , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(1), fma(m1.getDoubleAt(6) , m2.getDoubleAt(2), fma(m1.getDoubleAt(7) , m2.getDoubleAt(3))))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(8) , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(1), fma(m1.getDoubleAt(10), m2.getDoubleAt(2), fma(m1.getDoubleAt(11), m2.getDoubleAt(3))))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(1), fma(m1.getDoubleAt(14), m2.getDoubleAt(2), fma(m1.getDoubleAt(15), m2.getDoubleAt(3))))));
					} else {
						temp.setVectorAt(0, fma(m1.getDoubleAt(0) , m2.getDoubleAt(16), fma(m1.getDoubleAt(1) , m2.getDoubleAt(17), fma(m1.getDoubleAt(2) , m2.getDoubleAt(18), fma(m1.getDoubleAt(3) , m2.getDoubleAt(19))))));
						temp.setVectorAt(1, fma(m1.getDoubleAt(4) , m2.getDoubleAt(16), fma(m1.getDoubleAt(5) , m2.getDoubleAt(17), fma(m1.getDoubleAt(6) , m2.getDoubleAt(18), fma(m1.getDoubleAt(7) , m2.getDoubleAt(19))))));
						temp.setVectorAt(2, fma(m1.getDoubleAt(8) , m2.getDoubleAt(16), fma(m1.getDoubleAt(9) , m2.getDoubleAt(17), fma(m1.getDoubleAt(10), m2.getDoubleAt(18), fma(m1.getDoubleAt(11), m2.getDoubleAt(19))))));
						temp.setVectorAt(3, fma(m1.getDoubleAt(12), m2.getDoubleAt(16), fma(m1.getDoubleAt(13), m2.getDoubleAt(17), fma(m1.getDoubleAt(14), m2.getDoubleAt(18), fma(m1.getDoubleAt(15), m2.getDoubleAt(19))))));
					}
				} else if(m1 != temp) {
					temp.setVectorAt(4, fma(m1.getDoubleAt(0) , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(1), fma(m1.getDoubleAt(2) , m2.getDoubleAt(2), fma(m1.getDoubleAt(3) , m2.getDoubleAt(3))))));
					temp.setVectorAt(5, fma(m1.getDoubleAt(4) , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(1), fma(m1.getDoubleAt(6) , m2.getDoubleAt(2), fma(m1.getDoubleAt(7) , m2.getDoubleAt(3))))));
					temp.setVectorAt(6, fma(m1.getDoubleAt(8) , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(1), fma(m1.getDoubleAt(10), m2.getDoubleAt(2), fma(m1.getDoubleAt(11), m2.getDoubleAt(3))))));
					temp.setVectorAt(7, fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(1), fma(m1.getDoubleAt(14), m2.getDoubleAt(2), fma(m1.getDoubleAt(15), m2.getDoubleAt(3))))));
				} else if(m2 != temp) {
					temp.setVectorAt(16, fma(m1.getDoubleAt(0) , m2.getDoubleAt(0), fma(m1.getDoubleAt(1) , m2.getDoubleAt(1), fma(m1.getDoubleAt(2) , m2.getDoubleAt(2), fma(m1.getDoubleAt(3) , m2.getDoubleAt(3))))));
					temp.setVectorAt(17, fma(m1.getDoubleAt(4) , m2.getDoubleAt(0), fma(m1.getDoubleAt(5) , m2.getDoubleAt(1), fma(m1.getDoubleAt(6) , m2.getDoubleAt(2), fma(m1.getDoubleAt(7) , m2.getDoubleAt(3))))));
					temp.setVectorAt(18, fma(m1.getDoubleAt(8) , m2.getDoubleAt(0), fma(m1.getDoubleAt(9) , m2.getDoubleAt(1), fma(m1.getDoubleAt(10), m2.getDoubleAt(2), fma(m1.getDoubleAt(11), m2.getDoubleAt(3))))));
					temp.setVectorAt(19, fma(m1.getDoubleAt(12), m2.getDoubleAt(0), fma(m1.getDoubleAt(13), m2.getDoubleAt(1), fma(m1.getDoubleAt(14), m2.getDoubleAt(2), fma(m1.getDoubleAt(15), m2.getDoubleAt(3))))));
				} else {
					temp.setVectorAt(20, fma(m1.getDoubleAt(0) , m2.getDoubleAt(16), fma(m1.getDoubleAt(1) , m2.getDoubleAt(17), fma(m1.getDoubleAt(2) , m2.getDoubleAt(18), fma(m1.getDoubleAt(3) , m2.getDoubleAt(19))))));
					temp.setVectorAt(21, fma(m1.getDoubleAt(4) , m2.getDoubleAt(16), fma(m1.getDoubleAt(5) , m2.getDoubleAt(17), fma(m1.getDoubleAt(6) , m2.getDoubleAt(18), fma(m1.getDoubleAt(7) , m2.getDoubleAt(19))))));
					temp.setVectorAt(22, fma(m1.getDoubleAt(8) , m2.getDoubleAt(16), fma(m1.getDoubleAt(9) , m2.getDoubleAt(17), fma(m1.getDoubleAt(10), m2.getDoubleAt(18), fma(m1.getDoubleAt(11), m2.getDoubleAt(19))))));
					temp.setVectorAt(23, fma(m1.getDoubleAt(12), m2.getDoubleAt(16), fma(m1.getDoubleAt(13), m2.getDoubleAt(17), fma(m1.getDoubleAt(14), m2.getDoubleAt(18), fma(m1.getDoubleAt(15), m2.getDoubleAt(19))))));
				} return temp;
			} throw new IllegalStateException();
		}
	}
	public static final boolean USE_MATRIX_FMA = Boolean.parseBoolean(System.getProperty("openglutils.matrix.usefma", "false"));
	public static Object multiplyByMatrix(Object m1, Object m2, int mM1, int nM1, int mM2, int nM2, TempVec _temp) {
		// M -> ROW               N -> COLUMN
//		ReferencedCallback.PVoidReferencedCallback MATRIX_MUL_checkArgs = (args) -> {
//			if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
//			if(m1 == m2) throw new Error();
//			if(!(m1 instanceof double[]) && !(m1 instanceof TempVec)) throw new Error();
//			if(!(m2 instanceof double[]) && !(m2 instanceof TempVec)) throw new Error();
//			if(nM1 != mM2) throw new Error();
//			if(_temp == null) return;
//			int expecetedSize = mM1 * nM2;
//			if(m1 == _temp) expecetedSize += mM1 * nM1;
//			if(m2 == _temp) expecetedSize += mM2 * nM2;
//			if(((TempVec) _temp).size < expecetedSize) throw new Error();
//		};
//		ReferencedCallback.PVoidReferencedCallback MATRIX_MUL_set = (args) -> {
//			Object temp = args[0];
//			int y = (int) args[1]; int x = (int) args[2];
//			double data = (double) args[3];
//			int offset = 0;
//			if(m1 == temp) offset += mM1 * nM1;
//			if(m2 == temp) offset += mM2 * nM2;
//			if(temp instanceof TempVec) ((TempVec) temp).setVectorAt(y * nM2 + x + offset, data);
//			else ((double[]) temp)[y * nM2 + x] = data;
//		};
//		ReferencedCallback.PDoubleReferencedCallback MATRIX_MUL_get = (args) -> {
//			Object temp = args[0];
//			Object mTarget = args[1];
//			int y = (int) args[2]; int x = (int) args[3];
//			int matrix = mTarget == m1 ? 1 : mTarget == m2 ? 2 : 0;
//			if(mTarget == temp) {
//				int tempOffset = 0;
//				if(matrix == 1) return (double) ((TempVec) temp).getVectorAt(y * nM1 + x + tempOffset);
//				tempOffset += (m1 == temp ? mM1 * nM1 : 0);
//				if(matrix == 2) return (double) ((TempVec) temp).getVectorAt(y * nM2 + x + tempOffset);
//				tempOffset += (m2 == temp ? mM2 * nM2 : 0);
//				return (double) ((TempVec) temp).getVectorAt(y * nM2 + x + tempOffset);
//			}
//			if(mTarget instanceof TempVec) return (double) ((TempVec) mTarget).getVectorAt(y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x);
//			return ((double[]) mTarget)[y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x];
//		};

		// This creates heap
//		MATRIX_MUL_checkArgs.get();
		if(USE_MATRIX_FMA) {
			if(mM1 == 4 && nM1 == 4 && mM2 == 4 && nM2 == 4) return multiplyByMatrix_4x4_4x4_FMA(m1, m2, _temp);
			if(mM1 == 4 && nM1 == 4 && mM2 == 4 && nM2 == 1) return multiplyByMatrix_4x4_4x1_FMA(m1, m2, _temp);
			if(mM1 == 3 && nM1 == 3 && mM2 == 3 && nM2 == 3) return multiplyByMatrix_3x3_3x3_FMA(m1, m2, _temp);
			if(mM1 == 3 && nM1 == 3 && mM2 == 3 && nM2 == 1) return multiplyByMatrix_3x3_3x1_FMA(m1, m2, _temp);
			if(mM1 == 2 && nM1 == 2 && mM2 == 2 && nM2 == 2) return multiplyByMatrix_2x2_2x2_FMA(m1, m2, _temp);
			if(mM1 == 2 && nM1 == 2 && mM2 == 2 && nM2 == 1) return multiplyByMatrix_2x2_2x1_FMA(m1, m2, _temp);
			{
				if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
				if(m1 == m2) throw new Error();
				if(!(m1 instanceof double[]) && !(m1 instanceof TempVec)) throw new Error();
				if(!(m2 instanceof double[]) && !(m2 instanceof TempVec)) throw new Error();
				if(nM1 != mM2) throw new Error();
				if(_temp != null) {
					int expecetedSize = mM1 * nM2;
					if(m1 == _temp) expecetedSize += mM1 * nM1;
					if(m2 == _temp) expecetedSize += mM2 * nM2;
					if(((TempVec) _temp).size < expecetedSize) throw new Error();
				}
			}
			Object temp = _temp == null ? new double[mM1 * nM2] : _temp;
			for(int i = 0; i < mM1; i++)
				for(int j = 0; j < nM2; j++) {
					double result = 0;
					for(int k = 0; k < nM1; k++) {
						double a, b;
						{
							Object mTarget = m1; int y = i; int x = k;
							int matrix = mTarget == m1 ? 1 : mTarget == m2 ? 2 : 0;
							if(mTarget == temp) {
								int tempOffset = 0;
								if(matrix == 1) a = ((TempVec) temp).getDoubleAt(y * nM1 + x + tempOffset);
								else {
									tempOffset += (m1 == temp ? mM1 * nM1 : 0);
									if(matrix == 2) a = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									else {
										tempOffset += (m2 == temp ? mM2 * nM2 : 0);
										a = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									}
								}
							} else {
								if(mTarget instanceof TempVec) a = ((TempVec) mTarget).getDoubleAt(y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x);
								else a = ((double[]) mTarget)[y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x];
							}
						}
						{
							Object mTarget = m2; int y = k; int x = j;
							int matrix = mTarget == m1 ? 1 : mTarget == m2 ? 2 : 0;
							if(mTarget == temp) {
								int tempOffset = 0;
								if(matrix == 1) b = ((TempVec) temp).getDoubleAt(y * nM1 + x + tempOffset);
								else {
									tempOffset += (m1 == temp ? mM1 * nM1 : 0);
									if(matrix == 2) b = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									else {
										tempOffset += (m2 == temp ? mM2 * nM2 : 0);
										b = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									}
								}
							} else {
								if(mTarget instanceof TempVec) b = ((TempVec) mTarget).getDoubleAt(y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x);
								else b = ((double[]) mTarget)[y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x];
							}
						} result = fma(a, b, result);
					}
					{
						int y = i; int x = j;
						double data = result;
						int offset = 0;
						if(m1 == temp) offset += mM1 * nM1;
						if(m2 == temp) offset += mM2 * nM2;
						if(temp instanceof TempVec) ((TempVec) temp).setVectorAt(y * nM2 + x + offset, data);
						else ((double[]) temp)[y * nM2 + x] = data;
					}
				}
			return temp;
		} else {
			if(mM1 == 4 && nM1 == 4 && mM2 == 4 && nM2 == 4) return multiplyByMatrix_4x4_4x4(m1, m2, _temp);
			if(mM1 == 4 && nM1 == 4 && mM2 == 4 && nM2 == 1) return multiplyByMatrix_4x4_4x1(m1, m2, _temp);
			if(mM1 == 3 && nM1 == 3 && mM2 == 3 && nM2 == 3) return multiplyByMatrix_3x3_3x3(m1, m2, _temp);
			if(mM1 == 3 && nM1 == 3 && mM2 == 3 && nM2 == 1) return multiplyByMatrix_3x3_3x1(m1, m2, _temp);
			if(mM1 == 2 && nM1 == 2 && mM2 == 2 && nM2 == 2) return multiplyByMatrix_2x2_2x2(m1, m2, _temp);
			if(mM1 == 2 && nM1 == 2 && mM2 == 2 && nM2 == 1) return multiplyByMatrix_2x2_2x1(m1, m2, _temp);
			{
				if(_temp != null && !(_temp instanceof TempVec)) throw new Error();
				if(m1 == m2) throw new Error();
				if(!(m1 instanceof double[]) && !(m1 instanceof TempVec)) throw new Error();
				if(!(m2 instanceof double[]) && !(m2 instanceof TempVec)) throw new Error();
				if(nM1 != mM2) throw new Error();
				if(_temp != null) {
					int expecetedSize = mM1 * nM2;
					if(m1 == _temp) expecetedSize += mM1 * nM1;
					if(m2 == _temp) expecetedSize += mM2 * nM2;
					if(((TempVec) _temp).size < expecetedSize) throw new Error();
				}
			}
			Object temp = _temp == null ? new double[mM1 * nM2] : _temp;
			for(int i = 0; i < mM1; i++)
				for(int j = 0; j < nM2; j++) {
					double result = 0;
					for(int k = 0; k < nM1; k++) {
						double a, b;
						{
							Object mTarget = m1; int y = i; int x = k;
							int matrix = mTarget == m1 ? 1 : mTarget == m2 ? 2 : 0;
							if(mTarget == temp) {
								int tempOffset = 0;
								if(matrix == 1) a = ((TempVec) temp).getDoubleAt(y * nM1 + x + tempOffset);
								else {
									tempOffset += (m1 == temp ? mM1 * nM1 : 0);
									if(matrix == 2) a = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									else {
										tempOffset += (m2 == temp ? mM2 * nM2 : 0);
										a = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									}
								}
							} else {
								if(mTarget instanceof TempVec) a = ((TempVec) mTarget).getDoubleAt(y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x);
								else a = ((double[]) mTarget)[y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x];
							}
						}
						{
							Object mTarget = m2; int y = k; int x = j;
							int matrix = mTarget == m1 ? 1 : mTarget == m2 ? 2 : 0;
							if(mTarget == temp) {
								int tempOffset = 0;
								if(matrix == 1) b = ((TempVec) temp).getDoubleAt(y * nM1 + x + tempOffset);
								else {
									tempOffset += (m1 == temp ? mM1 * nM1 : 0);
									if(matrix == 2) b = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									else {
										tempOffset += (m2 == temp ? mM2 * nM2 : 0);
										b = ((TempVec) temp).getDoubleAt(y * nM2 + x + tempOffset);
									}
								}
							} else {
								if(mTarget instanceof TempVec) b = ((TempVec) mTarget).getDoubleAt(y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x);
								else b = ((double[]) mTarget)[y * (matrix == 1 ? nM1 : matrix == 2 ? nM2 : 0) + x];
							}
						} result += a * b;
					}
					{
						int y = i; int x = j;
						double data = result;
						int offset = 0;
						if(m1 == temp) offset += mM1 * nM1;
						if(m2 == temp) offset += mM2 * nM2;
						if(temp instanceof TempVec) ((TempVec) temp).setVectorAt(y * nM2 + x + offset, data);
						else ((double[]) temp)[y * nM2 + x] = data;
					}
				}
			return temp;
		}
	}

	public static MatMxN multiplyByMatrix(MatMxN m1, MatMxN m2) { return mat(m1.m, m2.n, (double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, double[] m2, int m, int n) { return mat(m1.m, n, (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m, n, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, TempVec m2, int m, int n) { return mat(m1.m, n, (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m, n, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, Vec2 m2) { return mat(m1.m, 1, (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, 2, 1, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, Vec3 m2) { return mat(m1.m, 1, (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, 3, 1, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, Vec4 m2) { return mat(m1.m, 1, (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, 4, 1, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, VecN m2) { return mat(m1.m, 1, (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m2.size, 1, null)); }
	public static MatMxN multiplyByMatrix(MatMxN m1, MatMxN m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2); multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, temp); MatMxN result = new MatMxN(m1.m, m2.n); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(MatMxN m1, double[] m2, int m, int n, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2, m, n); multiplyByMatrix(m1.d, m2, m1.m, m1.n, m, n, temp); MatMxN result = new MatMxN(m1.m, n); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(MatMxN m1, TempVec m2, int m, int n, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2, m, n); multiplyByMatrix(m1.d, m2, m1.m, m1.n, m, n, temp); MatMxN result = new MatMxN(m1.m, n); temp.cutTo(result, 0, (m2 == temp ? 1 : 0) * m2.size, result.size); return result; }
	public static MatMxN multiplyByMatrix(MatMxN m1, Vec2 m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2); multiplyByMatrix(m1.d, m2, m1.m, m1.n, 2, 1, temp); MatMxN result = new MatMxN(m1.m, 1); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(MatMxN m1, Vec3 m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2); multiplyByMatrix(m1.d, m2, m1.m, m1.n, 3, 1, temp); MatMxN result = new MatMxN(m1.m, 1); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(MatMxN m1, Vec4 m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2); multiplyByMatrix(m1.d, m2, m1.m, m1.n, 4, 1, temp); MatMxN result = new MatMxN(m1.m, 1); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(MatMxN m1, VecN m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m2); multiplyByMatrix(m1.d, m2, m1.m, m1.n, m2.size, 1, temp); MatMxN result = new MatMxN(m1.m, 1); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(double[] m1, int m, int n, MatMxN m2) { return mat(m, m2.n, (double[]) multiplyByMatrix(m1, m2.d, m, n, m2.m, m2.n, null)); }
	public static MatMxN multiplyByMatrix(TempVec m1, int m, int n, MatMxN m2) { return mat(m, m2.n, (double[]) multiplyByMatrix(m1, m2.d, m, n, m2.m, m2.n, null)); }
	public static MatMxN multiplyByMatrix(double[] m1, int m, int n, MatMxN m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m, n, m2); multiplyByMatrix(m1, m2.d, m, n, m2.m, m2.n, temp); MatMxN result = new MatMxN(m, m2.n); temp.cutTo(result, 0, 0, result.size); return result; }
	public static MatMxN multiplyByMatrix(TempVec m1, int m, int n, MatMxN m2, TempVec temp) { if(temp == null) return multiplyByMatrix(m1, m, n, m2); multiplyByMatrix(m1, m2.d, m, n, m2.m, m2.n, temp); MatMxN result = new MatMxN(m, m2.n); temp.cutTo(result, 0, (m1 == temp ? 1 : 0) * m1.size, result.size); return result; }

	public static MatMxN multiplyXByMatrix(MatMxN m1, MatMxN m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, temp); temp.cutTo(m1, 0, 0, m1.m * m2.n); return m1; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, double[] m2, int m, int n, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m2, m, n); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2, m1.m, m1.n, m, n, temp); temp.cutTo(m1, 0, 0, m1.m * n); return m1; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, TempVec m2, int m, int n, TempVec temp) { if(temp == null) temp = m2; multiplyByMatrix(m1.d, m2, m1.m, m1.n, m, n, temp); temp.cutTo(m1, 0, 0, m1.m * n); return m1; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, Vec2 m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 2, 1, temp); temp.cutTo(m1, 0, 0, m1.m); return m1; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, Vec3 m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 3, 1, temp); temp.cutTo(m1, 0, 0, m1.m); return m1; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, Vec4 m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 4, 1, temp); temp.cutTo(m1, 0, 0, m1.m); return m1; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, VecN m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.size, 1, temp); temp.cutTo(m1, 0, 0, m1.m); return m1; }
	public static MatMxN multiplyXByMatrix(double[] m1, int m, int n, MatMxN m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m, n, m2); System.arraycopy(result.d, 0, m2.d, 0, result.size); return m2; } multiplyByMatrix(m1, m2.d, m, n, m2.m, m2.n, temp); temp.cutTo(m2, 0, 0, m * m2.n); return m2; }
	public static MatMxN multiplyXByMatrix(TempVec m1, int m, int n, MatMxN m2, TempVec temp) { if(temp == null) { MatMxN result = multiplyByMatrix(m1, m, n, m2); System.arraycopy(result.d, 0, m2.d, 0, result.size); return m2; } multiplyByMatrix(m1, m2.d, m, n, m2.m, m2.n, temp); temp.cutTo(m2, 0, 0, m * m2.n); return m2; }
	public static MatMxN multiplyXByMatrix(MatMxN m1, MatMxN m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatMxN multiplyXByMatrix(MatMxN m1, double[] m2, int m, int n) { return multiplyXByMatrix(m1, m2, m, n, null); }
	public static MatMxN multiplyXByMatrix(MatMxN m1, TempVec m2, int m, int n) { return multiplyXByMatrix(m1, m2, m, n, null); }
	public static MatMxN multiplyXByMatrix(MatMxN m1, Vec2 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatMxN multiplyXByMatrix(MatMxN m1, Vec3 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatMxN multiplyXByMatrix(MatMxN m1, Vec4 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatMxN multiplyXByMatrix(MatMxN m1, VecN m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatMxN multiplyXByMatrix(double[] m1, int m, int n, MatMxN m2) { return multiplyXByMatrix(m1, m, n, m2, null); }
	public static MatMxN multiplyXByMatrix(TempVec m1, int m, int n, MatMxN m2) { return multiplyXByMatrix(m1, m, n, m2, null); }

	// Multiply square matrix
	public static Mat2 multiplyByMatrix(Mat2 m1, Mat2 m2) { return mat2((double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, null)); }
	public static Mat3 multiplyByMatrix(Mat3 m1, Mat3 m2) { return mat3((double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, null)); }
	public static Mat4 multiplyByMatrix(Mat4 m1, Mat4 m2) { return mat4((double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, null)); }
	public static MatNxN multiplyByMatrix(MatNxN m1, MatNxN m2) { double[] result = (double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, null); return matN((int) sqrt(result.length), result); }
	public static Vec2 multiplyByMatrix(Mat2 m1, Vec2 m2) { return vec2((double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 2, 1, null)); }
	public static Vec3 multiplyByMatrix(Mat3 m1, Vec3 m2) { return vec3((double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 3, 1, null)); }
	public static Vec4 multiplyByMatrix(Mat4 m1, Vec4 m2) { return vec4((double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 4, 1, null)); }
	public static VecN multiplyByMatrix(MatNxN m1, VecN m2) { double[] result = (double[]) multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.size, 1, null); return vecn(result); }
	public static Mat2 multiplyByMatrix(Mat2 m1, double[] m2) { return mat2((double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, null)); }
	public static Mat3 multiplyByMatrix(Mat3 m1, double[] m2) { return mat3((double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, null)); }
	public static Mat4 multiplyByMatrix(Mat4 m1, double[] m2) { return mat4((double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, null)); }
	public static MatNxN multiplyByMatrix(MatNxN m1, double[] m2) { double[] result = (double[]) multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, null); return matN((int) sqrt(result.length), result); }
	public static Mat2 multiplyByMatrix(Mat2 m1, TempVec m2) { multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, m2); Mat2 result = new Mat2(); m2.cutTo(result, 0, 4, 4); return result; }
	public static Mat3 multiplyByMatrix(Mat3 m1, TempVec m2) { multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, m2); Mat3 result = new Mat3(); m2.cutTo(result, 0, 9, 9); return result; }
	public static Mat4 multiplyByMatrix(Mat4 m1, TempVec m2) { multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, m2); Mat4 result = new Mat4(); m2.cutTo(result, 0, 16, 16); return result; }
	public static MatNxN multiplyByMatrix(MatNxN m1, TempVec m2) { multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, m2); MatNxN result = new MatNxN(sqrt(m1.size)); m2.cutTo(result, 0, m1.size, m1.size); return result; }
	public static MatNxN multiplyByMatrix(TempVec m1, TempVec m2, int m, int n) { multiplyByMatrix(m1, m2, m, n, m, n, m2); MatNxN result = new MatNxN(m, n); m2.cutTo(result, 0, (m1 == m2 ? 2 : 1) * m * n, m * n); return result; }
	public static MatNxN multiplyByMatrix(TempVec m1, TempVec m2, int n) { return multiplyByMatrix(m1, m2, n, n); }

	public static Mat2 multiplyXByMatrix(Mat2 m1, Mat2 m2, TempVec temp) { if(temp == null) { Mat2 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 4); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, temp); temp.cutTo(m1, 0, 0, 4); return m1; }
	public static Mat3 multiplyXByMatrix(Mat3 m1, Mat3 m2, TempVec temp) { if(temp == null) { Mat3 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 9); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, temp); temp.cutTo(m1, 0, 0, 9); return m1; }
	public static Mat4 multiplyXByMatrix(Mat4 m1, Mat4 m2, TempVec temp) { if(temp == null) { Mat4 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 16); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, temp); temp.cutTo(m1, 0, 0, 16); return m1; }
	public static MatNxN multiplyXByMatrix(MatNxN m1, MatNxN m2, TempVec temp) { if(temp == null) { MatNxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.m, m2.n, temp); temp.cutTo(m1, 0, 0, m1.size); return m1; }
	public static Vec2 multiplyXByMatrix(Mat2 m1, Vec2 m2, TempVec temp) { if(temp == null) { Vec2 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 2); return m2; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 2, 1, temp); temp.cutTo(m2, 0, 0, 2); return m2; }
	public static Vec3 multiplyXByMatrix(Mat3 m1, Vec3 m2, TempVec temp) { if(temp == null) { Vec3 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 3); return m2; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 3, 1, temp); temp.cutTo(m2, 0, 0, 3); return m2; }
	public static Vec4 multiplyXByMatrix(Mat4 m1, Vec4 m2, TempVec temp) { if(temp == null) { Vec4 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 4); return m2; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, 4, 1, temp); temp.cutTo(m2, 0, 0, 4); return m2; }
	public static VecN multiplyXByMatrix(MatNxN m1, VecN m2, TempVec temp) { if(temp == null) { VecN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m2; } multiplyByMatrix(m1.d, m2.d, m1.m, m1.n, m2.size, 1, temp); temp.cutTo(m2, 0, 0, m2.size); return m2; }
	public static Mat2 multiplyXByMatrix(Mat2 m1, double[] m2, TempVec temp) { if(temp == null) { Mat2 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 4); return m1; } multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, 0, 4); return m1; }
	public static Mat3 multiplyXByMatrix(Mat3 m1, double[] m2, TempVec temp) { if(temp == null) { Mat3 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 9); return m1; } multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, 0, 9); return m1; }
	public static Mat4 multiplyXByMatrix(Mat4 m1, double[] m2, TempVec temp) { if(temp == null) { Mat4 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, 16); return m1; } multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, 0, 16); return m1; }
	public static MatNxN multiplyXByMatrix(MatNxN m1, double[] m2, TempVec temp) { if(temp == null) { MatNxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m1.d, 0, result.size); return m1; } multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, 0, m1.size); return m1; }
	public static Mat2 multiplyXByMatrix(Mat2 m1, TempVec m2, TempVec temp) { if(temp == null) temp = m2; multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, (m2 == temp ? 1 : 0) * 4, 4); return m1; }
	public static Mat3 multiplyXByMatrix(Mat3 m1, TempVec m2, TempVec temp) { if(temp == null) temp = m2; multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, (m2 == temp ? 1 : 0) * 9, 9); return m1; }
	public static Mat4 multiplyXByMatrix(Mat4 m1, TempVec m2, TempVec temp) { if(temp == null) temp = m2; multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, (m2 == temp ? 1 : 0) * 16, 16); return m1; }
	public static MatNxN multiplyXByMatrix(MatNxN m1, TempVec m2, TempVec temp) { if(temp == null) temp = m2; multiplyByMatrix(m1.d, m2, m1.m, m1.n, m1.m, m1.n, temp); temp.cutTo(m1, 0, (m2 == temp ? 1 : 0) * m1.size, m1.size); return m1; }
	public static Mat2 multiplyXByMatrix(Mat2 m1, Mat2 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat3 multiplyXByMatrix(Mat3 m1, Mat3 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat4 multiplyXByMatrix(Mat4 m1, Mat4 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatNxN multiplyXByMatrix(MatNxN m1, MatNxN m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Vec2 multiplyXByMatrix(Mat2 m1, Vec2 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Vec3 multiplyXByMatrix(Mat3 m1, Vec3 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Vec4 multiplyXByMatrix(Mat4 m1, Vec4 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static VecN multiplyXByVecrix(MatNxN m1, VecN m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat2 multiplyXByMatrix(Mat2 m1, double[] m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat3 multiplyXByMatrix(Mat3 m1, double[] m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat4 multiplyXByMatrix(Mat4 m1, double[] m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatNxN multiplyXByMatrix(MatNxN m1, double[] m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat2 multiplyXByMatrix(Mat2 m1, TempVec m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat3 multiplyXByMatrix(Mat3 m1, TempVec m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat4 multiplyXByMatrix(Mat4 m1, TempVec m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatNxN multiplyXByMatrix(MatNxN m1, TempVec m2) { return multiplyXByMatrix(m1, m2, null); }

	public static Mat2 multiplyByMatrix(double[] m1, Mat2 m2) { return mat2((double[]) multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, null)); }
	public static Mat3 multiplyByMatrix(double[] m1, Mat3 m2) { return mat3((double[]) multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, null)); }
	public static Mat4 multiplyByMatrix(double[] m1, Mat4 m2) { return mat4((double[]) multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, null)); }
	public static MatNxN multiplyByMatrix(double[] m1, MatNxN m2) { double[] result = (double[]) multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, null); return matN((int) sqrt(result.length), result); }
	public static Mat2 multiplyByMatrix(TempVec m1, Mat2 m2) { multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, m1); Mat2 result = new Mat2(); m1.cutTo(result, 0, 4, 4); return result; }
	public static Mat3 multiplyByMatrix(TempVec m1, Mat3 m2) { multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, m1); Mat3 result = new Mat3(); m1.cutTo(result, 0, 9, 9); return result; }
	public static Mat4 multiplyByMatrix(TempVec m1, Mat4 m2) { multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, m1); Mat4 result = new Mat4(); m1.cutTo(result, 0, 16, 16); return result; }
	public static MatNxN multiplyByMatrix(TempVec m1, MatNxN m2) { multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, m1); MatNxN result = new MatNxN(sqrt(m1.size)); m1.cutTo(result, 0, m2.size, m2.size); return result; }
	public static Mat2 multiplyXByMatrix(double[] m1, Mat2 m2, TempVec temp) { if(temp == null) { Mat2 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m2.d, 0, 4); return m2; } multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, 0, 4); return m2; }
	public static Mat3 multiplyXByMatrix(double[] m1, Mat3 m2, TempVec temp) { if(temp == null) { Mat3 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m2.d, 0, 9); return m2; } multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, 0, 9); return m2; }
	public static Mat4 multiplyXByMatrix(double[] m1, Mat4 m2, TempVec temp) { if(temp == null) { Mat4 result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m2.d, 0, 16); return m2; } multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, 0, 16); return m2; }
	public static MatNxN multiplyXByMatrix(double[] m1, MatNxN m2, TempVec temp) { if(temp == null) { MatNxN result = multiplyByMatrix(m1, m2); System.arraycopy(result.d, 0, m2.d, 0, result.size); return m2; } multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, 0, m2.size); return m2; }
	public static Mat2 multiplyXByMatrix(TempVec m1, Mat2 m2, TempVec temp) { if(temp == null) temp = m1; multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, (m1 == temp ? 1 : 0) * 4, 4); return m2; }
	public static Mat3 multiplyXByMatrix(TempVec m1, Mat3 m2, TempVec temp) { if(temp == null) temp = m1; multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, (m1 == temp ? 1 : 0) * 9, 9); return m2; }
	public static Mat4 multiplyXByMatrix(TempVec m1, Mat4 m2, TempVec temp) { if(temp == null) temp = m1; multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, (m1 == temp ? 1 : 0) * 16, 16); return m2; }
	public static MatNxN multiplyXByMatrix(TempVec m1, MatNxN m2, TempVec temp) { if(temp == null) temp = m1; multiplyByMatrix(m1, m2.d, m2.m, m2.n, m2.m, m2.n, temp); temp.cutTo(m2, 0, (m1 == temp ? 1 : 0) * m2.size, m2.size); return m2; }
	public static Mat2 multiplyXByMatrix(double[] m1, Mat2 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat3 multiplyXByMatrix(double[] m1, Mat3 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat4 multiplyXByMatrix(double[] m1, Mat4 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatNxN multiplyXByMatrix(double[] m1, MatNxN m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat2 multiplyXByMatrix(TempVec m1, Mat2 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat3 multiplyXByMatrix(TempVec m1, Mat3 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static Mat4 multiplyXByMatrix(TempVec m1, Mat4 m2) { return multiplyXByMatrix(m1, m2, null); }
	public static MatNxN multiplyXByMatrix(TempVec m1, MatNxN m2) { return multiplyXByMatrix(m1, m2, null); }

	// https://thebookofshaders.com/glossary/?search=lessThan
	public static BVec2 lessThan(Vec2 x, Vec2 y) { BVec2 result = bvec2(false); lessThan(x.d, y.d, result.d); return result; }
	public static BVec3 lessThan(Vec3 x, Vec3 y) { BVec3 result = bvec3(false); lessThan(x.d, y.d, result.d); return result; }
	public static BVec4 lessThan(Vec4 x, Vec4 y) { BVec4 result = bvec4(false); lessThan(x.d, y.d, result.d); return result; }
	public static BVecN lessThan(VecN x, VecN y) { BVecN result = bvecn(new boolean[x.size]); lessThan(x.d, y.d, result.d); return result; }
	public static BVec2 lessThanX(Vec2 x, Vec2 y, BVec2 result) { lessThan(x.d, y.d, result.d); return result; }
	public static BVec3 lessThanX(Vec3 x, Vec3 y, BVec3 result) { lessThan(x.d, y.d, result.d); return result; }
	public static BVec4 lessThanX(Vec4 x, Vec4 y, BVec4 result) { lessThan(x.d, y.d, result.d); return result; }
	public static BVecN lessThanX(VecN x, VecN y, BVecN result) { lessThan(x.d, y.d, result.d); return result; }

	// https://thebookofshaders.com/glossary/?search=lessThanEqual
	public static BVec2 lessThanEqual(Vec2 x, Vec2 y) { BVec2 result = bvec2(false); lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVec3 lessThanEqual(Vec3 x, Vec3 y) { BVec3 result = bvec3(false); lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVec4 lessThanEqual(Vec4 x, Vec4 y) { BVec4 result = bvec4(false); lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVecN lessThanEqual(VecN x, VecN y) { BVecN result = bvecn(new boolean[x.size]); lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVec2 lessThanEqualX(Vec2 x, Vec2 y, BVec2 result) { lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVec3 lessThanEqualX(Vec3 x, Vec3 y, BVec3 result) { lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVec4 lessThanEqualX(Vec4 x, Vec4 y, BVec4 result) { lessThanEqual(x.d, y.d, result.d); return result; }
	public static BVecN lessThanEqualX(VecN x, VecN y, BVecN result) { lessThanEqual(x.d, y.d, result.d); return result; }

	// https://thebookofshaders.com/glossary/?search=greaterThan
	public static BVec2 greaterThan(Vec2 x, Vec2 y) { BVec2 result = bvec2(false); greaterThan(x.d, y.d, result.d); return result; }
	public static BVec3 greaterThan(Vec3 x, Vec3 y) { BVec3 result = bvec3(false); greaterThan(x.d, y.d, result.d); return result; }
	public static BVec4 greaterThan(Vec4 x, Vec4 y) { BVec4 result = bvec4(false); greaterThan(x.d, y.d, result.d); return result; }
	public static BVecN greaterThan(VecN x, VecN y) { BVecN result = bvecn(new boolean[x.size]); greaterThan(x.d, y.d, result.d); return result; }
	public static BVec2 greaterThanX(Vec2 x, Vec2 y, BVec2 result) { greaterThan(x.d, y.d, result.d); return result; }
	public static BVec3 greaterThanX(Vec3 x, Vec3 y, BVec3 result) { greaterThan(x.d, y.d, result.d); return result; }
	public static BVec4 greaterThanX(Vec4 x, Vec4 y, BVec4 result) { greaterThan(x.d, y.d, result.d); return result; }
	public static BVecN greaterThanX(VecN x, VecN y, BVecN result) { greaterThan(x.d, y.d, result.d); return result; }

	// https://thebookofshaders.com/glossary/?search=greaterThanEqual
	public static BVec2 greaterThanEqual(Vec2 x, Vec2 y) { BVec2 result = bvec2(false); greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVec3 greaterThanEqual(Vec3 x, Vec3 y) { BVec3 result = bvec3(false); greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVec4 greaterThanEqual(Vec4 x, Vec4 y) { BVec4 result = bvec4(false); greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVecN greaterThanEqual(VecN x, VecN y) { BVecN result = bvecn(new boolean[x.size]); greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVec2 greaterThanEqualX(Vec2 x, Vec2 y, BVec2 result) { greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVec3 greaterThanEqualX(Vec3 x, Vec3 y, BVec3 result) { greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVec4 greaterThanEqualX(Vec4 x, Vec4 y, BVec4 result) { greaterThanEqual(x.d, y.d, result.d); return result; }
	public static BVecN greaterThanEqualX(VecN x, VecN y, BVecN result) { greaterThanEqual(x.d, y.d, result.d); return result; }

	// https://thebookofshaders.com/glossary/?search=equal
	public static BVec2 equal(Vec2 x, Vec2 y) { BVec2 result = bvec2(false); equal(x.d, y.d, result.d); return result; }
	public static BVec3 equal(Vec3 x, Vec3 y) { BVec3 result = bvec3(false); equal(x.d, y.d, result.d); return result; }
	public static BVec4 equal(Vec4 x, Vec4 y) { BVec4 result = bvec4(false); equal(x.d, y.d, result.d); return result; }
	public static BVecN equal(VecN x, VecN y) { BVecN result = bvecn(new boolean[x.size]); equal(x.d, y.d, result.d); return result; }
	public static BVec2 equalX(Vec2 x, Vec2 y, BVec2 result) { equal(x.d, y.d, result.d); return result; }
	public static BVec3 equalX(Vec3 x, Vec3 y, BVec3 result) { equal(x.d, y.d, result.d); return result; }
	public static BVec4 equalX(Vec4 x, Vec4 y, BVec4 result) { equal(x.d, y.d, result.d); return result; }
	public static BVecN equalX(VecN x, VecN y, BVecN result) { equal(x.d, y.d, result.d); return result; }

	// https://thebookofshaders.com/glossary/?search=notEqual
	public static BVec2 notEqual(Vec2 x, Vec2 y) { BVec2 result = bvec2(true); notEqual(x.d, y.d, result.d); return result; }
	public static BVec3 notEqual(Vec3 x, Vec3 y) { BVec3 result = bvec3(true); notEqual(x.d, y.d, result.d); return result; }
	public static BVec4 notEqual(Vec4 x, Vec4 y) { BVec4 result = bvec4(true); notEqual(x.d, y.d, result.d); return result; }
	public static BVecN notEqual(VecN x, VecN y) { BVecN result = bvecn(new boolean[x.size]); notEqual(x.d, y.d, result.d); return result; }
	public static BVec2 notEqualX(Vec2 x, Vec2 y, BVec2 result) { notEqual(x.d, y.d, result.d); return result; }
	public static BVec3 notEqualX(Vec3 x, Vec3 y, BVec3 result) { notEqual(x.d, y.d, result.d); return result; }
	public static BVec4 notEqualX(Vec4 x, Vec4 y, BVec4 result) { notEqual(x.d, y.d, result.d); return result; }
	public static BVecN notEqualX(VecN x, VecN y, BVecN result) { notEqual(x.d, y.d, result.d); return result; }

	// https://thebookofshaders.com/glossary/?search=not
	public static BVec2 not(BVec2 x) { return _bvec2(BooleanUtils.not(x.d)); }
	public static BVec3 not(BVec3 x) { return _bvec3(BooleanUtils.not(x.d)); }
	public static BVec4 not(BVec4 x) { return _bvec4(BooleanUtils.not(x.d)); }
	public static BVecN not(BVecN x) { return _bvecn(BooleanUtils.not(x.d)); }
	public static BVec2 notX(BVec2 x) { BooleanUtils.not(x.d, x.d); return x; }
	public static BVec3 notX(BVec3 x) { BooleanUtils.not(x.d, x.d); return x; }
	public static BVec4 notX(BVec4 x) { BooleanUtils.not(x.d, x.d); return x; }
	public static BVecN notX(BVecN x) { BooleanUtils.not(x.d, x.d); return x; }

	// https://thebookofshaders.com/glossary/?search=any
	public static boolean all(BVec2 x) { return BooleanUtils.all(x.d); }
	public static boolean all(BVec3 x) { return BooleanUtils.all(x.d); }
	public static boolean all(BVec4 x) { return BooleanUtils.all(x.d); }
	public static boolean all(BVecN x) { return BooleanUtils.all(x.d); }

	// https://thebookofshaders.com/glossary/?search=all
	public static boolean any(BVec2 x) { return BooleanUtils.any(x.d); }
	public static boolean any(BVec3 x) { return BooleanUtils.any(x.d); }
	public static boolean any(BVec4 x) { return BooleanUtils.any(x.d); }
	public static boolean any(BVecN x) { return BooleanUtils.any(x.d); }

	// https://thebookofshaders.com/glossary/?search=add
	public static Vec2 add(Vec2 x, double y) { return vec2(x.x() + y, x.y() + y); }
	public static Vec2 add(Vec2 x, Vec2 y) { return vec2(x.x() + y.x(), x.y() + y.y()); }
	public static Vec2 add(Vec2 x, Vec3 y) { return vec2(x.x() + y.x(), x.y() + y.y()); }
	public static Vec2 add(Vec2 x, Vec4 y) { return vec2(x.x() + y.x(), x.y() + y.y()); }
	public static Vec3 add(Vec3 x, double y) { return vec3(x.x() + y, x.y() + y, x.z() + y); }
	public static Vec3 add(Vec3 x, Vec3 y) { return vec3(x.x() + y.x(), x.y() + y.y(), x.z() + y.z()); }
	public static Vec3 add(Vec3 x, Vec4 y) { return vec3(x.x() + y.x(), x.y() + y.y(), x.z() + y.z()); }
	public static Vec3 add(Vec3 x, Vec2 y) { return vec3(x.x() + y.x(), x.y() + y.y(), x.z()); }
	public static Vec4 add(Vec4 x, double y) { return vec4(x.x() + y, x.y() + y, x.z() + y, x.w() + y); }
	public static Vec4 add(Vec4 x, Vec4 y) { return vec4(x.x() + y.x(), x.y() + y.y(), x.z() + y.z(), x.w() + y.w()); }
	public static Vec4 add(Vec4 x, Vec3 y) { return vec4(x.x() + y.x(), x.y() + y.y(), x.z() + y.z(), x.w()); }
	public static Vec4 add(Vec4 x, Vec2 y) { return vec4(x.x() + y.x(), x.y() + y.y(), x.z(), x.w()); }
	public static VecN add(VecN x, double y) { VecN result = vecn(x); for(int i = 0; i < result.size; i++) result.d[i] += y; return result; }
	public static VecN add(VecN x, VecN y) { VecN result = vecn((int) min(x.size, y.size)); for(int i = 0; i < result.size; i++) result.d[i] = x.d[i] + y.d[i]; return result; }

	public static Vec2 addX(Vec2 x, double y) { x.x(x.x() + y); x.y(x.y() + y); return x; }
	public static Vec2 addX(Vec2 x, Vec2 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); return x; }
	public static Vec2 addX(Vec2 x, Vec3 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); return x; }
	public static Vec2 addX(Vec2 x, Vec4 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); return x; }
	public static Vec3 addX(Vec3 x, double y) { x.x(x.x() + y); x.y(x.y() + y); x.z(x.z() + y); return x; }
	public static Vec3 addX(Vec3 x, Vec3 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); x.z(x.z() + y.z()); return x; }
	public static Vec3 addX(Vec3 x, Vec4 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); x.z(x.z() + y.z()); return x; }
	public static Vec3 addX(Vec3 x, Vec2 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); return x; }
	public static Vec4 addX(Vec4 x, double y) { x.x(x.x() + y); x.y(x.y() + y); x.z(x.z() + y); x.w(x.w() + y); return x; }
	public static Vec4 addX(Vec4 x, Vec4 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); x.z(x.z() + y.z()); x.w(x.w() + y.w()); return x; }
	public static Vec4 addX(Vec4 x, Vec3 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); x.z(x.z() + y.z()); return x; }
	public static Vec4 addX(Vec4 x, Vec2 y) { x.x(x.x() + y.x()); x.y(x.y() + y.y()); return x; }
	public static VecN addX(VecN x, double y) { for(int i = 0; i < x.size; i++) x.d[i] += y; return x; }
	public static VecN addX(VecN x, VecN y) { int min = (int) min(x.size, y.size); for(int i = 0; i < min; i++) x.d[i] += y.d[i]; return x; }

	// https://thebookofshaders.com/glossary/?search=subtract
	public static Vec2 sub(Vec2 x, double y) { return vec2(x.x() - y, x.y() - y); }
	public static Vec2 sub(Vec2 x, Vec2 y) { return vec2(x.x() - y.x(), x.y() - y.y()); }
	public static Vec2 sub(Vec2 x, Vec3 y) { return vec2(x.x() - y.x(), x.y() - y.y()); }
	public static Vec2 sub(Vec2 x, Vec4 y) { return vec2(x.x() - y.x(), x.y() - y.y()); }
	public static Vec3 sub(Vec3 x, double y) { return vec3(x.x() - y, x.y() - y, x.z() - y); }
	public static Vec3 sub(Vec3 x, Vec3 y) { return vec3(x.x() - y.x(), x.y() - y.y(), x.z() - y.z()); }
	public static Vec3 sub(Vec3 x, Vec4 y) { return vec3(x.x() - y.x(), x.y() - y.y(), x.z() - y.z()); }
	public static Vec3 sub(Vec3 x, Vec2 y) { return vec3(x.x() - y.x(), x.y() - y.y(), x.z()); }
	public static Vec4 sub(Vec4 x, double y) { return vec4(x.x() - y, x.y() - y, x.z() - y, x.w() - y); }
	public static Vec4 sub(Vec4 x, Vec4 y) { return vec4(x.x() - y.x(), x.y() - y.y(), x.z() - y.z(), x.w() - y.w()); }
	public static Vec4 sub(Vec4 x, Vec3 y) { return vec4(x.x() - y.x(), x.y() - y.y(), x.z() - y.z(), x.w()); }
	public static Vec4 sub(Vec4 x, Vec2 y) { return vec4(x.x() - y.x(), x.y() - y.y(), x.z(), x.w()); }
	public static VecN sub(VecN x, double y) { VecN result = vecn(x); for(int i = 0; i < result.size; i++) result.d[i] -= y; return result; }
	public static VecN sub(VecN x, VecN y) { VecN result = vecn((int) min(x.size, y.size)); for(int i = 0; i < result.size; i++) result.d[i] = x.d[i] - y.d[i]; return result; }

	public static Vec2 subX(Vec2 x, double y) { x.x(x.x() - y); x.y(x.y() - y); return x; }
	public static Vec2 subX(Vec2 x, Vec2 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); return x; }
	public static Vec2 subX(Vec2 x, Vec3 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); return x; }
	public static Vec2 subX(Vec2 x, Vec4 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); return x; }
	public static Vec3 subX(Vec3 x, double y) { x.x(x.x() - y); x.y(x.y() - y); x.z(x.z() - y); return x; }
	public static Vec3 subX(Vec3 x, Vec3 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); x.z(x.z() - y.z()); return x; }
	public static Vec3 subX(Vec3 x, Vec4 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); x.z(x.z() - y.z()); return x; }
	public static Vec3 subX(Vec3 x, Vec2 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); return x; }
	public static Vec4 subX(Vec4 x, double y) { x.x(x.x() - y); x.y(x.y() - y); x.z(x.z() - y); x.w(x.w() - y); return x; }
	public static Vec4 subX(Vec4 x, Vec4 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); x.z(x.z() - y.z()); x.w(x.w() - y.w()); return x; }
	public static Vec4 subX(Vec4 x, Vec3 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); x.z(x.z() - y.z()); return x; }
	public static Vec4 subX(Vec4 x, Vec2 y) { x.x(x.x() - y.x()); x.y(x.y() - y.y()); return x; }
	public static VecN subX(VecN x, double y) { for(int i = 0; i < x.size; i++) x.d[i] -= y; return x; }
	public static VecN subX(VecN x, VecN y) { int min = (int) min(x.size, y.size); for(int i = 0; i < min; i++) x.d[i] -= y.d[i]; return x; }

	// https://thebookofshaders.com/glossary/?search=multiply
	public static Vec2 mul(Vec2 x, double y) { return vec2(x.x() * y, x.y() * y); }
	public static Vec2 mul(Vec2 x, Vec2 y) { return vec2(x.x() * y.x(), x.y() * y.y()); }
	public static Vec2 mul(Vec2 x, Vec3 y) { return vec2(x.x() * y.x(), x.y() * y.y()); }
	public static Vec2 mul(Vec2 x, Vec4 y) { return vec2(x.x() * y.x(), x.y() * y.y()); }
	public static Vec3 mul(Vec3 x, double y) { return vec3(x.x() * y, x.y() * y, x.z() * y); }
	public static Vec3 mul(Vec3 x, Vec3 y) { return vec3(x.x() * y.x(), x.y() * y.y(), x.z() * y.z()); }
	public static Vec3 mul(Vec3 x, Vec4 y) { return vec3(x.x() * y.x(), x.y() * y.y(), x.z() * y.z()); }
	public static Vec3 mul(Vec3 x, Vec2 y) { return vec3(x.x() * y.x(), x.y() * y.y(), x.z()); }
	public static Vec4 mul(Vec4 x, double y) { return vec4(x.x() * y, x.y() * y, x.z() * y, x.w() * y); }
	public static Vec4 mul(Vec4 x, Vec4 y) { return vec4(x.x() * y.x(), x.y() * y.y(), x.z() * y.z(), x.w() * y.w()); }
	public static Vec4 mul(Vec4 x, Vec3 y) { return vec4(x.x() * y.x(), x.y() * y.y(), x.z() * y.z(), x.w()); }
	public static Vec4 mul(Vec4 x, Vec2 y) { return vec4(x.x() * y.x(), x.y() * y.y(), x.z(), x.w()); }
	public static VecN mul(VecN x, double y) { VecN result = vecn(x); for(int i = 0; i < result.size; i++) result.d[i] *= y; return result; }
	public static VecN mul(VecN x, VecN y) { VecN result = vecn((int) min(x.size, y.size)); for(int i = 0; i < result.size; i++) result.d[i] = x.d[i] * y.d[i]; return result; }

	public static Vec2 mulX(Vec2 x, double y) { x.x(x.x() * y); x.y(x.y() * y); return x; }
	public static Vec2 mulX(Vec2 x, Vec2 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); return x; }
	public static Vec2 mulX(Vec2 x, Vec3 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); return x; }
	public static Vec2 mulX(Vec2 x, Vec4 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); return x; }
	public static Vec3 mulX(Vec3 x, double y) { x.x(x.x() * y); x.y(x.y() * y); x.z(x.z() * y); return x; }
	public static Vec3 mulX(Vec3 x, Vec3 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); x.z(x.z() * y.z()); return x; }
	public static Vec3 mulX(Vec3 x, Vec4 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); x.z(x.z() * y.z()); return x; }
	public static Vec3 mulX(Vec3 x, Vec2 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); return x; }
	public static Vec4 mulX(Vec4 x, double y) { x.x(x.x() * y); x.y(x.y() * y); x.z(x.z() * y); x.w(x.w() * y); return x; }
	public static Vec4 mulX(Vec4 x, Vec4 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); x.z(x.z() * y.z()); x.w(x.w() * y.w()); return x; }
	public static Vec4 mulX(Vec4 x, Vec3 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); x.z(x.z() * y.z()); return x; }
	public static Vec4 mulX(Vec4 x, Vec2 y) { x.x(x.x() * y.x()); x.y(x.y() * y.y()); return x; }
	public static VecN mulX(VecN x, double y) { for(int i = 0; i < x.size; i++) x.d[i] *= y; return x; }
	public static VecN mulX(VecN x, VecN y) { int min = (int) min(x.size, y.size); for(int i = 0; i < min; i++) x.d[i] *= y.d[i]; return x; }

	// https://thebookofshaders.com/glossary/?search=divide
	public static Vec2 div(Vec2 x, double y) { return vec2(x.x() / y, x.y() / y); }
	public static Vec2 div(Vec2 x, Vec2 y) { return vec2(x.x() / y.x(), x.y() / y.y()); }
	public static Vec2 div(Vec2 x, Vec3 y) { return vec2(x.x() / y.x(), x.y() / y.y()); }
	public static Vec2 div(Vec2 x, Vec4 y) { return vec2(x.x() / y.x(), x.y() / y.y()); }
	public static Vec3 div(Vec3 x, double y) { return vec3(x.x() / y, x.y() / y, x.z() / y); }
	public static Vec3 div(Vec3 x, Vec3 y) { return vec3(x.x() / y.x(), x.y() / y.y(), x.z() / y.z()); }
	public static Vec3 div(Vec3 x, Vec4 y) { return vec3(x.x() / y.x(), x.y() / y.y(), x.z() / y.z()); }
	public static Vec3 div(Vec3 x, Vec2 y) { return vec3(x.x() / y.x(), x.y() / y.y(), x.z()); }
	public static Vec4 div(Vec4 x, double y) { return vec4(x.x() / y, x.y() / y, x.z() / y, x.w() / y); }
	public static Vec4 div(Vec4 x, Vec4 y) { return vec4(x.x() / y.x(), x.y() / y.y(), x.z() / y.z(), x.w() / y.w()); }
	public static Vec4 div(Vec4 x, Vec3 y) { return vec4(x.x() / y.x(), x.y() / y.y(), x.z() / y.z(), x.w()); }
	public static Vec4 div(Vec4 x, Vec2 y) { return vec4(x.x() / y.x(), x.y() / y.y(), x.z(), x.w()); }
	public static VecN div(VecN x, double y) { VecN result = vecn(x); for(int i = 0; i < result.size; i++) result.d[i] /= y; return result; }
	public static VecN div(VecN x, VecN y) { VecN result = vecn((int) min(x.size, y.size)); for(int i = 0; i < result.size; i++) result.d[i] = x.d[i] / y.d[i]; return result; }

	public static Vec2 divX(Vec2 x, double y) { x.x(x.x() / y); x.y(x.y() / y); return x; }
	public static Vec2 divX(Vec2 x, Vec2 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); return x; }
	public static Vec2 divX(Vec2 x, Vec3 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); return x; }
	public static Vec2 divX(Vec2 x, Vec4 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); return x; }
	public static Vec3 divX(Vec3 x, double y) { x.x(x.x() / y); x.y(x.y() / y); x.z(x.z() / y); return x; }
	public static Vec3 divX(Vec3 x, Vec3 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); x.z(x.z() / y.z()); return x; }
	public static Vec3 divX(Vec3 x, Vec4 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); x.z(x.z() / y.z()); return x; }
	public static Vec3 divX(Vec3 x, Vec2 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); return x; }
	public static Vec4 divX(Vec4 x, double y) { x.x(x.x() / y); x.y(x.y() / y); x.z(x.z() / y); x.w(x.w() / y); return x; }
	public static Vec4 divX(Vec4 x, Vec4 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); x.z(x.z() / y.z()); x.w(x.w() / y.w()); return x; }
	public static Vec4 divX(Vec4 x, Vec3 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); x.z(x.z() / y.z()); return x; }
	public static Vec4 divX(Vec4 x, Vec2 y) { x.x(x.x() / y.x()); x.y(x.y() / y.y()); return x; }
	public static VecN divX(VecN x, double y) { for(int i = 0; i < x.size; i++) x.d[i] /= y; return x; }
	public static VecN divX(VecN x, VecN y) { int min = (int) min(x.size, y.size); for(int i = 0; i < min; i++) x.d[i] /= y.d[i]; return x; }

	// https://thebookofshaders.com/glossary/?search=mod
	public static double mod(double x, double y) { return x % y; }
	public static Vec2 mod(Vec2 x, double y) { return vec2(x.x() % y, x.y() % y); }
	public static Vec2 mod(Vec2 x, Vec2 y) { return vec2(x.x() % y.x(), x.y() % y.y()); }
	public static Vec2 mod(Vec2 x, Vec3 y) { return vec2(x.x() % y.x(), x.y() % y.y()); }
	public static Vec2 mod(Vec2 x, Vec4 y) { return vec2(x.x() % y.x(), x.y() % y.y()); }
	public static Vec3 mod(Vec3 x, double y) { return vec3(x.x() % y, x.y() % y, x.z() % y); }
	public static Vec3 mod(Vec3 x, Vec3 y) { return vec3(x.x() % y.x(), x.y() % y.y(), x.z() % y.z()); }
	public static Vec3 mod(Vec3 x, Vec4 y) { return vec3(x.x() % y.x(), x.y() % y.y(), x.z() % y.z()); }
	public static Vec3 mod(Vec3 x, Vec2 y) { return vec3(x.x() % y.x(), x.y() % y.y(), x.z()); }
	public static Vec4 mod(Vec4 x, double y) { return vec4(x.x() % y, x.y() % y, x.z() % y, x.w() % y); }
	public static Vec4 mod(Vec4 x, Vec4 y) { return vec4(x.x() % y.x(), x.y() % y.y(), x.z() % y.z(), x.w() % y.w()); }
	public static Vec4 mod(Vec4 x, Vec3 y) { return vec4(x.x() % y.x(), x.y() % y.y(), x.z() % y.z(), x.w()); }
	public static Vec4 mod(Vec4 x, Vec2 y) { return vec4(x.x() % y.x(), x.y() % y.y(), x.z(), x.w()); }
	public static VecN mod(VecN x, double y) { VecN result = vecn(x); for(int i = 0; i < result.size; i++) result.d[i] %= y; return result; }
	public static VecN mod(VecN x, VecN y) { VecN result = vecn((int) min(x.size, y.size)); for(int i = 0; i < result.size; i++) result.d[i] = x.d[i] % y.d[i]; return result; }

	public static Vec2 modX(Vec2 x, double y) { x.x(x.x() % y); x.y(x.y() % y); return x; }
	public static Vec2 modX(Vec2 x, Vec2 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); return x; }
	public static Vec2 modX(Vec2 x, Vec3 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); return x; }
	public static Vec2 modX(Vec2 x, Vec4 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); return x; }
	public static Vec3 modX(Vec3 x, double y) { x.x(x.x() % y); x.y(x.y() % y); x.z(x.z() % y); return x; }
	public static Vec3 modX(Vec3 x, Vec3 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); x.z(x.z() % y.z()); return x; }
	public static Vec3 modX(Vec3 x, Vec4 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); x.z(x.z() % y.z()); return x; }
	public static Vec3 modX(Vec3 x, Vec2 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); return x; }
	public static Vec4 modX(Vec4 x, double y) { x.x(x.x() % y); x.y(x.y() % y); x.z(x.z() % y); x.w(x.w() % y); return x; }
	public static Vec4 modX(Vec4 x, Vec4 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); x.z(x.z() % y.z()); x.w(x.w() % y.w()); return x; }
	public static Vec4 modX(Vec4 x, Vec3 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); x.z(x.z() % y.z()); return x; }
	public static Vec4 modX(Vec4 x, Vec2 y) { x.x(x.x() % y.x()); x.y(x.y() % y.y()); return x; }
	public static VecN modX(VecN x, double y) { for(int i = 0; i < x.size; i++) x.d[i] %= y; return x; }
	public static VecN modX(VecN x, VecN y) { int min = (int) min(x.size, y.size); for(int i = 0; i < min; i++) x.d[i] %= y.d[i]; return x; }

	public static Vec2 vec2(double a) { if(PoolCleaner.isAccepted(Vec2.class)) { Vec2 result = Pool.tryBorrow(Pool.getPool(Vec2.class)); result.set(a, a); return result; } return new Vec2(a); }
	public static Vec3 vec3(double a) { if(PoolCleaner.isAccepted(Vec3.class)) { Vec3 result = Pool.tryBorrow(Pool.getPool(Vec3.class)); result.set(a, a, a); return result; } return new Vec3(a); }
	public static Vec4 vec4(double a) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(a, a, a, a); return result; } return new Vec4(a); }
	public static Vec2 vec2(Vec2 xy) { if(PoolCleaner.isAccepted(Vec2.class)) { Vec2 result = Pool.tryBorrow(Pool.getPool(Vec2.class)); result.set(xy.x(), xy.y()); return result; } return new Vec2(xy.x(), xy.y()); }
	public static Vec2 vec2(double x, double y) { if(PoolCleaner.isAccepted(Vec2.class)) { Vec2 result = Pool.tryBorrow(Pool.getPool(Vec2.class)); result.set(x, y); return result; } return new Vec2(x, y); }
	public static Vec3 vec3(double x, Vec2 yz) { if(PoolCleaner.isAccepted(Vec3.class)) { Vec3 result = Pool.tryBorrow(Pool.getPool(Vec3.class)); result.set(x, yz.x(), yz.y()); return result; } return new Vec3(x, yz.x(), yz.y()); }
	public static Vec3 vec3(Vec2 xy, double z) { if(PoolCleaner.isAccepted(Vec3.class)) { Vec3 result = Pool.tryBorrow(Pool.getPool(Vec3.class)); result.set(xy.x(), xy.y(), z); return result; } return new Vec3(xy.x(), xy.y(), z); }
	public static Vec3 vec3(Vec3 xyz) { if(PoolCleaner.isAccepted(Vec3.class)) { Vec3 result = Pool.tryBorrow(Pool.getPool(Vec3.class)); result.set(xyz.x(), xyz.y(), xyz.z()); return result; } return new Vec3(xyz.x(), xyz.y(), xyz.z()); }
	public static Vec3 vec3(double x, double y, double z) { if(PoolCleaner.isAccepted(Vec3.class)) { Vec3 result = Pool.tryBorrow(Pool.getPool(Vec3.class)); result.set(x, y, z); return result; } return new Vec3(x, y, z); }
	public static Vec4 vec4(double x, double y, Vec2 zw) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(x, y, zw.x(), zw.y()); return result; } return new Vec4(x, y, zw.x(), zw.y()); }
	public static Vec4 vec4(double x, Vec2 yz, double w) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(x, yz.x(), yz.y(), w); return result; } return new Vec4(x, yz.x(), yz.y(), w); }
	public static Vec4 vec4(Vec2 xy, double z, double w) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(xy.x(), xy.y(), z, w); return result; } return new Vec4(xy.x(), xy.y(), z, w); }
	public static Vec4 vec4(Vec2 xy, Vec2 zw) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(xy.x(), xy.y(), zw.x(), zw.y()); return result; } return new Vec4(xy.x(), xy.y(), zw.x(), zw.y()); }
	public static Vec4 vec4(double x, Vec3 yzw) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(x, yzw.x(), yzw.y(), yzw.z()); return result; } return new Vec4(x, yzw.x(), yzw.y(), yzw.z()); }
	public static Vec4 vec4(Vec3 xyz, double w) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(xyz.x(), xyz.y(), xyz.z(), w); return result; } return new Vec4(xyz.x(), xyz.y(), xyz.z(), w); }
	public static Vec4 vec4(Vec4 xyzw) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); return result; } return new Vec4(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); }
	public static Vec4 vec4(double x, double y, double z, double w) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(x, y, z, w); return result; } return new Vec4(x, y, z, w); }
	public static Vec2 vec2(double... d) { if(PoolCleaner.isAccepted(Vec2.class)) { Vec2 result = Pool.tryBorrow(Pool.getPool(Vec2.class)); result.set(d[0], d[1]); return result; } return new Vec2(d); }
	public static Vec3 vec3(double... d) { if(PoolCleaner.isAccepted(Vec3.class)) { Vec3 result = Pool.tryBorrow(Pool.getPool(Vec3.class)); result.set(d[0], d[1], d[2]); return result; } return new Vec3(d); }
	public static Vec4 vec4(double... d) { if(PoolCleaner.isAccepted(Vec4.class)) { Vec4 result = Pool.tryBorrow(Pool.getPool(Vec4.class)); result.set(d[0], d[1], d[2], d[3]); return result; } return new Vec4(d); }
	public static VecN vecn(double... d) { return new VecN(d); }
	public static Vec2 _vec2(double... d) { return new Vec2(false, d); }
	public static Vec3 _vec3(double... d) { return new Vec3(false, d); }
	public static Vec4 _vec4(double... d) { return new Vec4(false, d); }
	public static VecN _vecn(double... d) { return new VecN(false, d); }
	public static VecN vecn(int size) { return new VecN(size); }
	public static VecN vecn(VecN... d) { int size = 0; for(GenType _d : d) size += _d.size; double[] ds = new double[size]; int j = 0; for(VecN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new VecN(ds); }
	public static VecN vecn(MatMxN... d) { int size = 0; for(GenType _d : d) size += _d.size; double[] ds = new double[size]; int j = 0; for(MatMxN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new VecN(ds); }

	public static FVec2 fvec2(float a) { if(PoolCleaner.isAccepted(FVec2.class)) { FVec2 result = Pool.tryBorrow(Pool.getPool(FVec2.class)); result.set(a, a); return result; } return new FVec2(a); }
	public static FVec3 fvec3(float a) { if(PoolCleaner.isAccepted(FVec3.class)) { FVec3 result = Pool.tryBorrow(Pool.getPool(FVec3.class)); result.set(a, a, a); return result; } return new FVec3(a); }
	public static FVec4 fvec4(float a) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(a, a, a, a); return result; } return new FVec4(a); }
	public static FVec2 fvec2(FVec2 xy) { if(PoolCleaner.isAccepted(FVec2.class)) { FVec2 result = Pool.tryBorrow(Pool.getPool(FVec2.class)); result.set(xy.x(), xy.y()); return result; } return new FVec2(xy.x(), xy.y()); }
	public static FVec2 fvec2(float x, float y) { if(PoolCleaner.isAccepted(FVec2.class)) { FVec2 result = Pool.tryBorrow(Pool.getPool(FVec2.class)); result.set(x, y); return result; } return new FVec2(x, y); }
	public static FVec3 fvec3(float x, FVec2 yz) { if(PoolCleaner.isAccepted(FVec3.class)) { FVec3 result = Pool.tryBorrow(Pool.getPool(FVec3.class)); result.set(x, yz.x(), yz.y()); return result; } return new FVec3(x, yz.x(), yz.y()); }
	public static FVec3 fvec3(FVec2 xy, float z) { if(PoolCleaner.isAccepted(FVec3.class)) { FVec3 result = Pool.tryBorrow(Pool.getPool(FVec3.class)); result.set(xy.x(), xy.y(), z); return result; } return new FVec3(xy.x(), xy.y(), z); }
	public static FVec3 fvec3(FVec3 xyz) { if(PoolCleaner.isAccepted(FVec3.class)) { FVec3 result = Pool.tryBorrow(Pool.getPool(FVec3.class)); result.set(xyz.x(), xyz.y(), xyz.z()); return result; } return new FVec3(xyz.x(), xyz.y(), xyz.z()); }
	public static FVec3 fvec3(float x, float y, float z) { if(PoolCleaner.isAccepted(FVec3.class)) { FVec3 result = Pool.tryBorrow(Pool.getPool(FVec3.class)); result.set(x, y, z); return result; } return new FVec3(x, y, z); }
	public static FVec4 fvec4(float x, float y, FVec2 zw) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(x, y, zw.x(), zw.y()); return result; } return new FVec4(x, y, zw.x(), zw.y()); }
	public static FVec4 fvec4(float x, FVec2 yz, float w) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(x, yz.x(), yz.y(), w); return result; } return new FVec4(x, yz.x(), yz.y(), w); }
	public static FVec4 fvec4(FVec2 xy, float z, float w) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(xy.x(), xy.y(), z, w); return result; } return new FVec4(xy.x(), xy.y(), z, w); }
	public static FVec4 fvec4(FVec2 xy, FVec2 zw) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(xy.x(), xy.y(), zw.x(), zw.y()); return result; } return new FVec4(xy.x(), xy.y(), zw.x(), zw.y()); }
	public static FVec4 fvec4(float x, FVec3 yzw) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(x, yzw.x(), yzw.y(), yzw.z()); return result; } return new FVec4(x, yzw.x(), yzw.y(), yzw.z()); }
	public static FVec4 fvec4(FVec3 xyz, float w) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(xyz.x(), xyz.y(), xyz.z(), w); return result; } return new FVec4(xyz.x(), xyz.y(), xyz.z(), w); }
	public static FVec4 fvec4(FVec4 xyzw) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); return result; } return new FVec4(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); }
	public static FVec4 fvec4(float x, float y, float z, float w) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(x, y, z, w); return result; } return new FVec4(x, y, z, w); }
	public static FVec2 fvec2(float... d) { if(PoolCleaner.isAccepted(FVec2.class)) { FVec2 result = Pool.tryBorrow(Pool.getPool(FVec2.class)); result.set(d[0], d[1]); return result; } return new FVec2(d); }
	public static FVec3 fvec3(float... d) { if(PoolCleaner.isAccepted(FVec3.class)) { FVec3 result = Pool.tryBorrow(Pool.getPool(FVec3.class)); result.set(d[0], d[1], d[2]); return result; } return new FVec3(d); }
	public static FVec4 fvec4(float... d) { if(PoolCleaner.isAccepted(FVec4.class)) { FVec4 result = Pool.tryBorrow(Pool.getPool(FVec4.class)); result.set(d[0], d[1], d[2], d[3]); return result; } return new FVec4(d); }
	public static FVecN fvecn(float... d) { return new FVecN(d); }
	public static FVec2 _fvec2(float... d) { return new FVec2(false, d); }
	public static FVec3 _fvec3(float... d) { return new FVec3(false, d); }
	public static FVec4 _fvec4(float... d) { return new FVec4(false, d); }
	public static FVecN _fvecn(float... d) { return new FVecN(false, d); }
	public static FVecN fvecn(int size) { return new FVecN(size); }
	public static FVecN fvecn(FVecN... d) { int size = 0; for(GenType _d : d) size += _d.size; float[] ds = new float[size]; int j = 0; for(FVecN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new FVecN(ds); }
	public static FVecN fvecn(FMatMxN... d) { int size = 0; for(GenType _d : d) size += _d.size; float[] ds = new float[size]; int j = 0; for(FMatMxN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new FVecN(ds); }

	public static LVec2 lvec2(long a) { if(PoolCleaner.isAccepted(LVec2.class)) { LVec2 result = Pool.tryBorrow(Pool.getPool(LVec2.class)); result.set(a, a); return result; } return new LVec2(a); }
	public static LVec3 lvec3(long a) { if(PoolCleaner.isAccepted(LVec3.class)) { LVec3 result = Pool.tryBorrow(Pool.getPool(LVec3.class)); result.set(a, a, a); return result; } return new LVec3(a); }
	public static LVec4 lvec4(long a) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(a, a, a, a); return result; } return new LVec4(a); }
	public static LVec2 lvec2(LVec2 xy) { if(PoolCleaner.isAccepted(LVec2.class)) { LVec2 result = Pool.tryBorrow(Pool.getPool(LVec2.class)); result.set(xy.x(), xy.y()); return result; } return new LVec2(xy.x(), xy.y()); }
	public static LVec2 lvec2(long x, long y) { if(PoolCleaner.isAccepted(LVec2.class)) { LVec2 result = Pool.tryBorrow(Pool.getPool(LVec2.class)); result.set(x, y); return result; } return new LVec2(x, y); }
	public static LVec3 lvec3(long x, LVec2 yz) { if(PoolCleaner.isAccepted(LVec3.class)) { LVec3 result = Pool.tryBorrow(Pool.getPool(LVec3.class)); result.set(x, yz.x(), yz.y()); return result; } return new LVec3(x, yz.x(), yz.y()); }
	public static LVec3 lvec3(LVec2 xy, long z) { if(PoolCleaner.isAccepted(LVec3.class)) { LVec3 result = Pool.tryBorrow(Pool.getPool(LVec3.class)); result.set(xy.x(), xy.y(), z); return result; } return new LVec3(xy.x(), xy.y(), z); }
	public static LVec3 lvec3(LVec3 xyz) { if(PoolCleaner.isAccepted(LVec3.class)) { LVec3 result = Pool.tryBorrow(Pool.getPool(LVec3.class)); result.set(xyz.x(), xyz.y(), xyz.z()); return result; } return new LVec3(xyz.x(), xyz.y(), xyz.z()); }
	public static LVec3 lvec3(long x, long y, long z) { if(PoolCleaner.isAccepted(LVec3.class)) { LVec3 result = Pool.tryBorrow(Pool.getPool(LVec3.class)); result.set(x, y, z); return result; } return new LVec3(x, y, z); }
	public static LVec4 lvec4(long x, long y, LVec2 zw) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(x, y, zw.x(), zw.y()); return result; } return new LVec4(x, y, zw.x(), zw.y()); }
	public static LVec4 lvec4(long x, LVec2 yz, long w) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(x, yz.x(), yz.y(), w); return result; } return new LVec4(x, yz.x(), yz.y(), w); }
	public static LVec4 lvec4(LVec2 xy, long z, long w) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(xy.x(), xy.y(), z, w); return result; } return new LVec4(xy.x(), xy.y(), z, w); }
	public static LVec4 lvec4(LVec2 xy, LVec2 zw) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(xy.x(), xy.y(), zw.x(), zw.y()); return result; } return new LVec4(xy.x(), xy.y(), zw.x(), zw.y()); }
	public static LVec4 lvec4(long x, LVec3 yzw) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(x, yzw.x(), yzw.y(), yzw.z()); return result; } return new LVec4(x, yzw.x(), yzw.y(), yzw.z()); }
	public static LVec4 lvec4(LVec3 xyz, long w) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(xyz.x(), xyz.y(), xyz.z(), w); return result; } return new LVec4(xyz.x(), xyz.y(), xyz.z(), w); }
	public static LVec4 lvec4(LVec4 xyzw) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); return result; } return new LVec4(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); }
	public static LVec4 lvec4(long x, long y, long z, long w) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(x, y, z, w); return result; } return new LVec4(x, y, z, w); }
	public static LVec2 lvec2(long... d) { if(PoolCleaner.isAccepted(LVec2.class)) { LVec2 result = Pool.tryBorrow(Pool.getPool(LVec2.class)); result.set(d[0], d[1]); return result; } return new LVec2(d); }
	public static LVec3 lvec3(long... d) { if(PoolCleaner.isAccepted(LVec3.class)) { LVec3 result = Pool.tryBorrow(Pool.getPool(LVec3.class)); result.set(d[0], d[1], d[2]); return result; } return new LVec3(d); }
	public static LVec4 lvec4(long... d) { if(PoolCleaner.isAccepted(LVec4.class)) { LVec4 result = Pool.tryBorrow(Pool.getPool(LVec4.class)); result.set(d[0], d[1], d[2], d[3]); return result; } return new LVec4(d); }
	public static LVecN lvecn(long... d) { return new LVecN(d); }
	public static LVec2 _lvec2(long... d) { return new LVec2(false, d); }
	public static LVec3 _lvec3(long... d) { return new LVec3(false, d); }
	public static LVec4 _lvec4(long... d) { return new LVec4(false, d); }
	public static LVecN _lvecn(long... d) { return new LVecN(false, d); }
	public static LVecN lvecn(int size) { return new LVecN(size); }
	public static LVecN lvecn(LVecN... d) { int size = 0; for(GenType _d : d) size += _d.size; long[] ds = new long[size]; int j = 0; for(LVecN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new LVecN(ds); }
	public static LVecN lvecn(LMatMxN... d) { int size = 0; for(GenType _d : d) size += _d.size; long[] ds = new long[size]; int j = 0; for(LMatMxN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new LVecN(ds); }

	public static IVec2 ivec2(int a) { if(PoolCleaner.isAccepted(IVec2.class)) { IVec2 result = Pool.tryBorrow(Pool.getPool(IVec2.class)); result.set(a, a); return result; } return new IVec2(a); }
	public static IVec3 ivec3(int a) { if(PoolCleaner.isAccepted(IVec3.class)) { IVec3 result = Pool.tryBorrow(Pool.getPool(IVec3.class)); result.set(a, a, a); return result; } return new IVec3(a); }
	public static IVec4 ivec4(int a) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(a, a, a, a); return result; } return new IVec4(a); }
	public static IVec2 ivec2(IVec2 xy) { if(PoolCleaner.isAccepted(IVec2.class)) { IVec2 result = Pool.tryBorrow(Pool.getPool(IVec2.class)); result.set(xy.x(), xy.y()); return result; } return new IVec2(xy.x(), xy.y()); }
	public static IVec2 ivec2(int x, int y) { if(PoolCleaner.isAccepted(IVec2.class)) { IVec2 result = Pool.tryBorrow(Pool.getPool(IVec2.class)); result.set(x, y); return result; } return new IVec2(x, y); }
	public static IVec3 ivec3(int x, IVec2 yz) { if(PoolCleaner.isAccepted(IVec3.class)) { IVec3 result = Pool.tryBorrow(Pool.getPool(IVec3.class)); result.set(x, yz.x(), yz.y()); return result; } return new IVec3(x, yz.x(), yz.y()); }
	public static IVec3 ivec3(IVec2 xy, int z) { if(PoolCleaner.isAccepted(IVec3.class)) { IVec3 result = Pool.tryBorrow(Pool.getPool(IVec3.class)); result.set(xy.x(), xy.y(), z); return result; } return new IVec3(xy.x(), xy.y(), z); }
	public static IVec3 ivec3(IVec3 xyz) { if(PoolCleaner.isAccepted(IVec3.class)) { IVec3 result = Pool.tryBorrow(Pool.getPool(IVec3.class)); result.set(xyz.x(), xyz.y(), xyz.z()); return result; } return new IVec3(xyz.x(), xyz.y(), xyz.z()); }
	public static IVec3 ivec3(int x, int y, int z) { if(PoolCleaner.isAccepted(IVec3.class)) { IVec3 result = Pool.tryBorrow(Pool.getPool(IVec3.class)); result.set(x, y, z); return result; } return new IVec3(x, y, z); }
	public static IVec4 ivec4(int x, int y, IVec2 zw) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(x, y, zw.x(), zw.y()); return result; } return new IVec4(x, y, zw.x(), zw.y()); }
	public static IVec4 ivec4(int x, IVec2 yz, int w) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(x, yz.x(), yz.y(), w); return result; } return new IVec4(x, yz.x(), yz.y(), w); }
	public static IVec4 ivec4(IVec2 xy, int z, int w) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(xy.x(), xy.y(), z, w); return result; } return new IVec4(xy.x(), xy.y(), z, w); }
	public static IVec4 ivec4(IVec2 xy, IVec2 zw) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(xy.x(), xy.y(), zw.x(), zw.y()); return result; } return new IVec4(xy.x(), xy.y(), zw.x(), zw.y()); }
	public static IVec4 ivec4(int x, IVec3 yzw) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(x, yzw.x(), yzw.y(), yzw.z()); return result; } return new IVec4(x, yzw.x(), yzw.y(), yzw.z()); }
	public static IVec4 ivec4(IVec3 xyz, int w) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(xyz.x(), xyz.y(), xyz.z(), w); return result; } return new IVec4(xyz.x(), xyz.y(), xyz.z(), w); }
	public static IVec4 ivec4(IVec4 xyzw) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); return result; } return new IVec4(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); }
	public static IVec4 ivec4(int x, int y, int z, int w) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(x, y, z, w); return result; } return new IVec4(x, y, z, w); }
	public static IVec2 ivec2(int... d) { if(PoolCleaner.isAccepted(IVec2.class)) { IVec2 result = Pool.tryBorrow(Pool.getPool(IVec2.class)); result.set(d[0], d[1]); return result; } return new IVec2(d); }
	public static IVec3 ivec3(int... d) { if(PoolCleaner.isAccepted(IVec3.class)) { IVec3 result = Pool.tryBorrow(Pool.getPool(IVec3.class)); result.set(d[0], d[1], d[2]); return result; } return new IVec3(d); }
	public static IVec4 ivec4(int... d) { if(PoolCleaner.isAccepted(IVec4.class)) { IVec4 result = Pool.tryBorrow(Pool.getPool(IVec4.class)); result.set(d[0], d[1], d[2], d[3]); return result; } return new IVec4(d); }
	public static IVecN ivecn(int... d) { return new IVecN(d); }
	public static IVec2 _ivec2(int... d) { return new IVec2(false, d); }
	public static IVec3 _ivec3(int... d) { return new IVec3(false, d); }
	public static IVec4 _ivec4(int... d) { return new IVec4(false, d); }
	public static IVecN _ivecn(int... d) { return new IVecN(false, d); }
	public static IVecN ivecn(int size) { return new IVecN(size); }
	public static IVecN ivecn(IVecN... d) { int size = 0; for(GenType _d : d) size += _d.size; int[] ds = new int[size]; int j = 0; for(IVecN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new IVecN(ds); }
	public static IVecN ivecn(IMatMxN... d) { int size = 0; for(GenType _d : d) size += _d.size; int[] ds = new int[size]; int j = 0; for(IMatMxN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new IVecN(ds); }

	public static SVec2 svec2(short a) { if(PoolCleaner.isAccepted(SVec2.class)) { SVec2 result = Pool.tryBorrow(Pool.getPool(SVec2.class)); result.set(a, a); return result; } return new SVec2(a); }
	public static SVec3 svec3(short a) { if(PoolCleaner.isAccepted(SVec3.class)) { SVec3 result = Pool.tryBorrow(Pool.getPool(SVec3.class)); result.set(a, a, a); return result; } return new SVec3(a); }
	public static SVec4 svec4(short a) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(a, a, a, a); return result; } return new SVec4(a); }
	public static SVec2 svec2(SVec2 xy) { if(PoolCleaner.isAccepted(SVec2.class)) { SVec2 result = Pool.tryBorrow(Pool.getPool(SVec2.class)); result.set(xy.x(), xy.y()); return result; } return new SVec2(xy.x(), xy.y()); }
	public static SVec2 svec2(short x, short y) { if(PoolCleaner.isAccepted(SVec2.class)) { SVec2 result = Pool.tryBorrow(Pool.getPool(SVec2.class)); result.set(x, y); return result; } return new SVec2(x, y); }
	public static SVec3 svec3(short x, SVec2 yz) { if(PoolCleaner.isAccepted(SVec3.class)) { SVec3 result = Pool.tryBorrow(Pool.getPool(SVec3.class)); result.set(x, yz.x(), yz.y()); return result; } return new SVec3(x, yz.x(), yz.y()); }
	public static SVec3 svec3(SVec2 xy, short z) { if(PoolCleaner.isAccepted(SVec3.class)) { SVec3 result = Pool.tryBorrow(Pool.getPool(SVec3.class)); result.set(xy.x(), xy.y(), z); return result; } return new SVec3(xy.x(), xy.y(), z); }
	public static SVec3 svec3(SVec3 xyz) { if(PoolCleaner.isAccepted(SVec3.class)) { SVec3 result = Pool.tryBorrow(Pool.getPool(SVec3.class)); result.set(xyz.x(), xyz.y(), xyz.z()); return result; } return new SVec3(xyz.x(), xyz.y(), xyz.z()); }
	public static SVec3 svec3(short x, short y, short z) { if(PoolCleaner.isAccepted(SVec3.class)) { SVec3 result = Pool.tryBorrow(Pool.getPool(SVec3.class)); result.set(x, y, z); return result; } return new SVec3(x, y, z); }
	public static SVec4 svec4(short x, short y, SVec2 zw) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(x, y, zw.x(), zw.y()); return result; } return new SVec4(x, y, zw.x(), zw.y()); }
	public static SVec4 svec4(short x, SVec2 yz, short w) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(x, yz.x(), yz.y(), w); return result; } return new SVec4(x, yz.x(), yz.y(), w); }
	public static SVec4 svec4(SVec2 xy, short z, short w) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(xy.x(), xy.y(), z, w); return result; } return new SVec4(xy.x(), xy.y(), z, w); }
	public static SVec4 svec4(SVec2 xy, SVec2 zw) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(xy.x(), xy.y(), zw.x(), zw.y()); return result; } return new SVec4(xy.x(), xy.y(), zw.x(), zw.y()); }
	public static SVec4 svec4(short x, SVec3 yzw) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(x, yzw.x(), yzw.y(), yzw.z()); return result; } return new SVec4(x, yzw.x(), yzw.y(), yzw.z()); }
	public static SVec4 svec4(SVec3 xyz, short w) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(xyz.x(), xyz.y(), xyz.z(), w); return result; } return new SVec4(xyz.x(), xyz.y(), xyz.z(), w); }
	public static SVec4 svec4(SVec4 xyzw) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); return result; } return new SVec4(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); }
	public static SVec4 svec4(short x, short y, short z, short w) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(x, y, z, w); return result; } return new SVec4(x, y, z, w); }
	public static SVec2 svec2(short... d) { if(PoolCleaner.isAccepted(SVec2.class)) { SVec2 result = Pool.tryBorrow(Pool.getPool(SVec2.class)); result.set(d[0], d[1]); return result; } return new SVec2(d); }
	public static SVec3 svec3(short... d) { if(PoolCleaner.isAccepted(SVec3.class)) { SVec3 result = Pool.tryBorrow(Pool.getPool(SVec3.class)); result.set(d[0], d[1], d[2]); return result; } return new SVec3(d); }
	public static SVec4 svec4(short... d) { if(PoolCleaner.isAccepted(SVec4.class)) { SVec4 result = Pool.tryBorrow(Pool.getPool(SVec4.class)); result.set(d[0], d[1], d[2], d[3]); return result; } return new SVec4(d); }
	public static SVecN svecn(short... d) { return new SVecN(d); }
	public static SVec2 _svec2(short... d) { return new SVec2(false, d); }
	public static SVec3 _svec3(short... d) { return new SVec3(false, d); }
	public static SVec4 _svec4(short... d) { return new SVec4(false, d); }
	public static SVecN _svecn(short... d) { return new SVecN(false, d); }
	public static SVecN svecn(int size) { return new SVecN(size); }
	public static SVecN svecn(SVecN... d) { int size = 0; for(GenType _d : d) size += _d.size; short[] ds = new short[size]; int j = 0; for(SVecN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new SVecN(ds); }
	public static SVecN svecn(SMatMxN... d) { int size = 0; for(GenType _d : d) size += _d.size; short[] ds = new short[size]; int j = 0; for(SMatMxN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new SVecN(ds); }

	public static BVec2 bvec2(boolean a) { if(PoolCleaner.isAccepted(BVec2.class)) { BVec2 result = Pool.tryBorrow(Pool.getPool(BVec2.class)); result.set(a, a); return result; } return new BVec2(a); }
	public static BVec3 bvec3(boolean a) { if(PoolCleaner.isAccepted(BVec3.class)) { BVec3 result = Pool.tryBorrow(Pool.getPool(BVec3.class)); result.set(a, a, a); return result; } return new BVec3(a); }
	public static BVec4 bvec4(boolean a) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(a, a, a, a); return result; } return new BVec4(a); }
	public static BVec2 bvec2(BVec2 xy) { if(PoolCleaner.isAccepted(BVec2.class)) { BVec2 result = Pool.tryBorrow(Pool.getPool(BVec2.class)); result.set(xy.x(), xy.y()); return result; } return new BVec2(xy.x(), xy.y()); }
	public static BVec2 bvec2(boolean x, boolean y) { if(PoolCleaner.isAccepted(BVec2.class)) { BVec2 result = Pool.tryBorrow(Pool.getPool(BVec2.class)); result.set(x, y); return result; } return new BVec2(x, y); }
	public static BVec3 bvec3(boolean x, BVec2 yz) { if(PoolCleaner.isAccepted(BVec3.class)) { BVec3 result = Pool.tryBorrow(Pool.getPool(BVec3.class)); result.set(x, yz.x(), yz.y()); return result; } return new BVec3(x, yz.x(), yz.y()); }
	public static BVec3 bvec3(BVec2 xy, boolean z) { if(PoolCleaner.isAccepted(BVec3.class)) { BVec3 result = Pool.tryBorrow(Pool.getPool(BVec3.class)); result.set(xy.x(), xy.y(), z); return result; } return new BVec3(xy.x(), xy.y(), z); }
	public static BVec3 bvec3(BVec3 xyz) { if(PoolCleaner.isAccepted(BVec3.class)) { BVec3 result = Pool.tryBorrow(Pool.getPool(BVec3.class)); result.set(xyz.x(), xyz.y(), xyz.z()); return result; } return new BVec3(xyz.x(), xyz.y(), xyz.z()); }
	public static BVec3 bvec3(boolean x, boolean y, boolean z) { if(PoolCleaner.isAccepted(BVec3.class)) { BVec3 result = Pool.tryBorrow(Pool.getPool(BVec3.class)); result.set(x, y, z); return result; } return new BVec3(x, y, z); }
	public static BVec4 bvec4(boolean x, boolean y, BVec2 zw) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(x, y, zw.x(), zw.y()); return result; } return new BVec4(x, y, zw.x(), zw.y()); }
	public static BVec4 bvec4(boolean x, BVec2 yz, boolean w) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(x, yz.x(), yz.y(), w); return result; } return new BVec4(x, yz.x(), yz.y(), w); }
	public static BVec4 bvec4(BVec2 xy, boolean z, boolean w) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(xy.x(), xy.y(), z, w); return result; } return new BVec4(xy.x(), xy.y(), z, w); }
	public static BVec4 bvec4(BVec2 xy, BVec2 zw) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(xy.x(), xy.y(), zw.x(), zw.y()); return result; } return new BVec4(xy.x(), xy.y(), zw.x(), zw.y()); }
	public static BVec4 bvec4(boolean x, BVec3 yzw) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(x, yzw.x(), yzw.y(), yzw.z()); return result; } return new BVec4(x, yzw.x(), yzw.y(), yzw.z()); }
	public static BVec4 bvec4(BVec3 xyz, boolean w) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(xyz.x(), xyz.y(), xyz.z(), w); return result; } return new BVec4(xyz.x(), xyz.y(), xyz.z(), w); }
	public static BVec4 bvec4(BVec4 xyzw) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); return result; } return new BVec4(xyzw.x(), xyzw.y(), xyzw.z(), xyzw.w()); }
	public static BVec4 bvec4(boolean x, boolean y, boolean z, boolean w) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(x, y, z, w); return result; } return new BVec4(x, y, z, w); }
	public static BVec2 bvec2(boolean... d) { if(PoolCleaner.isAccepted(BVec2.class)) { BVec2 result = Pool.tryBorrow(Pool.getPool(BVec2.class)); result.set(d[0], d[1]); return result; } return new BVec2(d); }
	public static BVec3 bvec3(boolean... d) { if(PoolCleaner.isAccepted(BVec3.class)) { BVec3 result = Pool.tryBorrow(Pool.getPool(BVec3.class)); result.set(d[0], d[1], d[2]); return result; } return new BVec3(d); }
	public static BVec4 bvec4(boolean... d) { if(PoolCleaner.isAccepted(BVec4.class)) { BVec4 result = Pool.tryBorrow(Pool.getPool(BVec4.class)); result.set(d[0], d[1], d[2], d[3]); return result; } return new BVec4(d); }
	public static BVecN bvecn(boolean... d) { return new BVecN(d); }
	public static BVec2 _bvec2(boolean... d) { return new BVec2(false, d); }
	public static BVec3 _bvec3(boolean... d) { return new BVec3(false, d); }
	public static BVec4 _bvec4(boolean... d) { return new BVec4(false, d); }
	public static BVecN _bvecn(boolean... d) { return new BVecN(false, d); }
	public static BVecN bvecn(int size) { return new BVecN(size); }
	public static BVecN bvecn(BVecN... d) { int size = 0; for(GenType _d : d) size += _d.size; boolean[] ds = new boolean[size]; int j = 0; for(BVecN _d : d) { System.arraycopy(_d.d, 0, ds, j, _d.size); j += _d.size; } return new BVecN(ds); }

	public static MatMxN _mat(int m, int n, double... d) { return new MatMxN(false, m, n, d); }
	public static MatMxN mat(int m, int n, double... d) { return new MatMxN(m, n, d); }
	public static MatNxN matN(int n, double... d) { return new MatNxN(n, d); }
	public static Mat2 mat2(double... d) { if(PoolCleaner.isAccepted(Mat2.class)) { Mat2 result = Pool.tryBorrow(Pool.getPool(Mat2.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new Mat2(d); }
	public static Mat3 mat3(double... d) { if(PoolCleaner.isAccepted(Mat3.class)) { Mat3 result = Pool.tryBorrow(Pool.getPool(Mat3.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new Mat3(d); }
	public static Mat4 mat4(double... d) { if(PoolCleaner.isAccepted(Mat4.class)) { Mat4 result = Pool.tryBorrow(Pool.getPool(Mat4.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new Mat4(d); }

	public static FMatMxN _fmat(int m, int n, float... d) { return new FMatMxN(false, m, n, d); }
	public static FMatMxN fmat(int m, int n, float... d) { return new FMatMxN(m, n, d); }
	public static FMatNxN fmatN(int n, float... d) { return new FMatNxN(n, d); }
	public static FMat2 fmat2(float... d) { if(PoolCleaner.isAccepted(FMat2.class)) { FMat2 result = Pool.tryBorrow(Pool.getPool(FMat2.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new FMat2(d); }
	public static FMat3 fmat3(float... d) { if(PoolCleaner.isAccepted(FMat3.class)) { FMat3 result = Pool.tryBorrow(Pool.getPool(FMat3.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new FMat3(d); }
	public static FMat4 fmat4(float... d) { if(PoolCleaner.isAccepted(FMat4.class)) { FMat4 result = Pool.tryBorrow(Pool.getPool(FMat4.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new FMat4(d); }

	public static LMatMxN _lmat(int m, int n, long... d) { return new LMatMxN(false, m, n, d); }
	public static LMatMxN lmat(int m, int n, long... d) { return new LMatMxN(m, n, d); }
	public static LMatNxN lmatN(int n, long... d) { return new LMatNxN(n, d); }
	public static LMat2 lmat2(long... d) { if(PoolCleaner.isAccepted(LMat2.class)) { LMat2 result = Pool.tryBorrow(Pool.getPool(LMat2.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new LMat2(d); }
	public static LMat3 lmat3(long... d) { if(PoolCleaner.isAccepted(LMat3.class)) { LMat3 result = Pool.tryBorrow(Pool.getPool(LMat3.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new LMat3(d); }
	public static LMat4 lmat4(long... d) { if(PoolCleaner.isAccepted(LMat4.class)) { LMat4 result = Pool.tryBorrow(Pool.getPool(LMat4.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new LMat4(d); }

	public static IMatMxN _imat(int m, int n, int... d) { return new IMatMxN(false, m, n, d); }
	public static IMatMxN imat(int m, int n, int... d) { return new IMatMxN(m, n, d); }
	public static IMatNxN imatN(int n, int... d) { return new IMatNxN(n, d); }
	public static IMat2 imat2(int... d) { if(PoolCleaner.isAccepted(IMat2.class)) { IMat2 result = Pool.tryBorrow(Pool.getPool(IMat2.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new IMat2(d); }
	public static IMat3 imat3(int... d) { if(PoolCleaner.isAccepted(IMat3.class)) { IMat3 result = Pool.tryBorrow(Pool.getPool(IMat3.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new IMat3(d); }
	public static IMat4 imat4(int... d) { if(PoolCleaner.isAccepted(IMat4.class)) { IMat4 result = Pool.tryBorrow(Pool.getPool(IMat4.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new IMat4(d); }

	public static SMatMxN _smat(int m, int n, short... d) { return new SMatMxN(false, m, n, d); }
	public static SMatMxN smat(int m, int n, short... d) { return new SMatMxN(m, n, d); }
	public static SMatNxN smatN(int n, short... d) { return new SMatNxN(n, d); }
	public static SMat2 smat2(short... d) { if(PoolCleaner.isAccepted(SMat2.class)) { SMat2 result = Pool.tryBorrow(Pool.getPool(SMat2.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new SMat2(d); }
	public static SMat3 smat3(short... d) { if(PoolCleaner.isAccepted(SMat3.class)) { SMat3 result = Pool.tryBorrow(Pool.getPool(SMat3.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new SMat3(d); }
	public static SMat4 smat4(short... d) { if(PoolCleaner.isAccepted(SMat4.class)) { SMat4 result = Pool.tryBorrow(Pool.getPool(SMat4.class)); System.arraycopy(d, 0, result.d, 0, result.d.length); return result; } return new SMat4(d); }

	public static Vec2 convert(Vec2f x) { return vec2(x.x, x.y); }
	public static Vec3 convert(Vec3f x) { return vec3(x.x, x.y, x.z); }
	public static Vec4 convert(Vec4f x) { return vec4(x.x, x.y, x.z, x.w); }
	public static Vec2f convert(Vec2 x) { return new Vec2f((float) x.x(), (float) x.y()); }
	public static Vec3f convert(Vec3 x) { return new Vec3f((float) x.x(), (float) x.y(), (float) x.z()); }
	public static Vec4f convert(Vec4 x) { return new Vec4f((float) x.x(), (float) x.y(), (float) x.z(), (float) x.w()); }

	public static class Matrix3D {
		public static MatNxN loadIdentity(MatNxN x) {
			for(int i = 0; i < x.m; i++)
				for(int j = 0; j < x.n; j++)
					x.d[x.getIndex0(i, j)] = i == j ? 1d : 0d;
			return x;
		}
		public static Mat4 loadIdentity(Mat4 x) { return (Mat4) loadIdentity((MatNxN) x); }
		public static Mat3 loadIdentity(Mat3 x) { return (Mat3) loadIdentity((MatNxN) x); }
		public static Mat2 loadIdentity(Mat2 x) { return (Mat2) loadIdentity((MatNxN) x); }
		public static MatMxN transpose(MatMxN x, TempVec temp) {
			if(temp == null) {
				double[] result = new double[x.size];
				for(int i = 0; i < x.m; i++)
					for(int j = 0; j < x.n; j++)
						result[x.getIndex0(i, j)] = x.d[x.getIndex0(j, i)];
				System.arraycopy(result, 0, x.d, 0, x.size); return x;
			}
			for(int i = 0; i < x.m; i++)
				for(int j = 0; j < x.n; j++)
					temp.setVectorAt(x.getIndex0(i, j), x.d[x.getIndex0(j, i)]);
			temp.cutTo(x, 0, 0, x.size); return x;
		}
		public static Mat4 transpose(Mat4 x, TempVec temp) { return (Mat4) transpose((MatMxN) x, temp); }
		public static Mat3 transpose(Mat3 x, TempVec temp) { return (Mat3) transpose((MatMxN) x, temp); }
		public static Mat2 transpose(Mat2 x, TempVec temp) { return (Mat2) transpose((MatMxN) x, temp); }
		public static double[] transpose(double[] x, int m, int n, TempVec temp) {
			if(temp == null) {
				double[] result = new double[x.length];
				for(int i = 0; i < m; i++)
					for(int j = 0; j < n; j++)
						result[i * n + j] = x[j * n + i];
				System.arraycopy(result, 0, x, 0, x.length); return x;
			}
			for(int i = 0; i < m; i++)
				for(int j = 0; j < n; j++)
					temp.setVectorAt(i * n + j, x[j * n + i]);
			for(int i = 0; i < x.length; i++) x[i] = temp.getDoubleAt(i);
			return x;
		}
		public static TempVec transpose(TempVec x, int m, int n) {
			int length = m * n;
			if(x.size < length * 2)
				throw new IllegalArgumentException();
			for(int i = 0; i < m; i++)
				for(int j = 0; j < n; j++)
					x.setVectorAt(i * n + j + length, x.getVectorAt(j * n + i));
			x.cutTo(x, 0, length, length); return x;
		}

		public static class Transformation {
			public static Mat4 translate(Mat4 x, double tx, double ty, double tz, TempVec temp) {
				if(temp == null) {
					double[] TRANSLATE_IDENTITY = new double[] {
//							1, 0, 0, tx,
//							0, 1, 0, ty,
//							0, 0, 1, tz,
//							0, 0, 0, 1
							1 , 0 , 0 , 0,
							0 , 1 , 0 , 0,
							0 , 0 , 1 , 0,
							tx, ty, tz, 1
					};
//					return multiplyXByMatrix(x, TRANSLATE_IDENTITY);
					return multiplyXByMatrix(TRANSLATE_IDENTITY, x);
				}
//				temp.setVectorAt(0, 1d) ; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, tx) ;
//				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, 1d) ; temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, ty) ;
//				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, 1d); temp.setVectorAt(11, tz);
//				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d); temp.setVectorAt(14, 0d); temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, 1d) ; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, 1d) ; temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, 1d); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, tx); temp.setVectorAt(13, ty); temp.setVectorAt(14, tz); temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			public static Mat4 translate(Mat4 x, Vec3 t, TempVec temp) {
				return translate(x, t.x(), t.y(), t.z(), temp);
			}
			public static Mat4 scale(Mat4 x, double sx, double sy, double sz, TempVec temp) {
				if(temp == null) {
					double[] SCALE_IDENTITY = new double[] {
//							sx, 0 , 0 , 0,
//							0 , sy, 0 , 0,
//							0 , 0 , sz, 0,
//							0 , 0 , 0 , 1
							sx, 0 , 0 , 0,
							0 , sy, 0 , 0,
							0 , 0 , sz, 0,
							0 , 0 , 0 , 1
					};
//					return multiplyXByMatrix(x, SCALE_IDENTITY);
					return multiplyXByMatrix(SCALE_IDENTITY, x);
				}
//				temp.setVectorAt(0, sx)	; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, 0d) ;
//				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, sy) ; temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, 0d) ;
//				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, sz); temp.setVectorAt(11, 0d);
//				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d); temp.setVectorAt(14, 0d); temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, sx)	; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, sy) ; temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, sz); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d); temp.setVectorAt(14, 0d); temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			public static Mat4 scale(Mat4 x, Vec3 s, TempVec temp) {
				return scale(x, s.x(), s.y(), s.z(), temp);
			}
			protected static Mat4 rotate_x(Mat4 x, double rx, TempVec temp) {
				if(temp == null) {
					double[] XROTATION_IDENTITY = new double[] {
//							1	    , 0		  , 0	    , 0,
//							0	    , cos(rx) , -sin(rx), 0,
//							0	    , sin(rx) , cos(rx) , 0,
//							0	    , 0		  , 0	    , 1
							1, 0	   , 0		, 0,
							0, cos(rx) , sin(rx), 0,
							0, -sin(rx), cos(rx), 0,
							0, 0	   , 0		, 1
					};
//					return multiplyXByMatrix(x, XROTATION_IDENTITY);
					return multiplyXByMatrix(XROTATION_IDENTITY, x);
				}
//				temp.setVectorAt(0, 1d)	; temp.setVectorAt(1, 0d)	   ; temp.setVectorAt(2, 0d)  	  ; temp.setVectorAt(3, 0d) ;
//				temp.setVectorAt(4, 0d)	; temp.setVectorAt(5, cos(rx)) ; temp.setVectorAt(6, -sin(rx)); temp.setVectorAt(7, 0d) ;
//				temp.setVectorAt(8, 0d)	; temp.setVectorAt(9, sin(rx)) ; temp.setVectorAt(10, cos(rx)); temp.setVectorAt(11, 0d);
//				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d)	   ; temp.setVectorAt(14, 0d) 	  ; temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, 1d)	; temp.setVectorAt(1, 0d)	   ; temp.setVectorAt(2, 0d)  	  ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, 0d)	; temp.setVectorAt(5, cos(rx)) ; temp.setVectorAt(6, sin(rx)) ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, 0d)	; temp.setVectorAt(9, -sin(rx)); temp.setVectorAt(10, cos(rx)); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d)	   ; temp.setVectorAt(14, 0d) 	  ; temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			protected static Mat4 rotate_y(Mat4 x, double ry, TempVec temp) {
				if(temp == null) {
					double[] YROTATION_IDENTITY = new double[] {
//							cos(ry) , 0		  , sin(ry) , 0,
//							0	    , 1		  , 0		, 0,
//							-sin(ry), 0 	  , cos(ry) , 0,
//							0	    , 0		  , 0	    , 1
							cos(ry) , 0		  , -sin(ry) , 0,
							0	    , 1		  , 0		, 0,
							sin(ry) , 0 	  , cos(ry) , 0,
							0	    , 0		  , 0	    , 1
					};
//					return multiplyXByMatrix(x, YROTATION_IDENTITY);
					return multiplyXByMatrix(YROTATION_IDENTITY, x);
				}
//				temp.setVectorAt(0, cos(ry)) ; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, sin(ry)) ; temp.setVectorAt(3, 0d) ;
//				temp.setVectorAt(4, 0d)		 ; temp.setVectorAt(5, 1d) ; temp.setVectorAt(6, 0d)	  ; temp.setVectorAt(7, 0d) ;
//				temp.setVectorAt(8, -sin(ry)); temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, cos(ry)); temp.setVectorAt(11, 0d);
//				temp.setVectorAt(12, 0d)	 ; temp.setVectorAt(13, 0d); temp.setVectorAt(14, 0d) 	  ; temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, cos(ry)) ; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, -sin(ry)); temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, 0d)		 ; temp.setVectorAt(5, 1d) ; temp.setVectorAt(6, 0d)	  ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, sin(ry)) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, cos(ry)); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, 0d)	 ; temp.setVectorAt(13, 0d); temp.setVectorAt(14, 0d) 	  ; temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			protected static Mat4 rotate_z(Mat4 x, double rz, TempVec temp) {
				if(temp == null) {
					double[] ZROTATION_IDENTITY = new double[] {
//							cos(rz) , -sin(rz), 0		, 0,
//							sin(rz) , cos(rz) , 0		, 0,
//							0		, 0 	  , 1		, 0,
//							0	    , 0		  , 0	    , 1
							cos(rz) , sin(rz) , 0		, 0,
							-sin(rz), cos(rz) , 0		, 0,
							0		, 0 	  , 1		, 0,
							0	    , 0		  , 0	    , 1
					};
//					return multiplyXByMatrix(x, ZROTATION_IDENTITY);
					return multiplyXByMatrix(ZROTATION_IDENTITY, x);
				}
//				temp.setVectorAt(0, cos(rz)) ; temp.setVectorAt(1, -sin(rz)); temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, 0d) ;
//				temp.setVectorAt(4, sin(rz)) ; temp.setVectorAt(5, cos(rz)) ; temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, 0d) ;
//				temp.setVectorAt(8, 0d)		 ; temp.setVectorAt(9, 0d) 		; temp.setVectorAt(10, 1d); temp.setVectorAt(11, 0d);
//				temp.setVectorAt(12, 0d)	 ; temp.setVectorAt(13, 0d)	    ; temp.setVectorAt(14, 0d); temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, cos(rz)) ; temp.setVectorAt(1, sin(rz))	; temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, -sin(rz)); temp.setVectorAt(5, cos(rz)) ; temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, 0d)		 ; temp.setVectorAt(9, 0d) 		; temp.setVectorAt(10, 1d); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, 0d)	 ; temp.setVectorAt(13, 0d)	    ; temp.setVectorAt(14, 0d); temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			public static Mat4 rotate_xyz(Mat4 x, double rx, double ry, double rz, TempVec temp) {
				rotate_x(x, rx, temp); rotate_y(x, ry, temp); rotate_z(x, rz, temp); return x;
			}
			public static Mat4 rotate_xyz(Mat4 x, Vec3 r, TempVec temp) {
				return rotate_xyz(x, r.x(), r.y(), r.z(), temp);
			}
			public static Mat4 rotate_zyx(Mat4 x, double rx, double ry, double rz, TempVec temp) {
				double sinA = sin(rx); double cosA = cos(rx);
				double sinB = sin(ry); double cosB = cos(ry);
				double sinC = sin(rz); double cosC = cos(rz);
				if(temp == null) {
					double[] ZYXROTATION_IDENTITY = new double[] {
//							cosC * cosB, (-sinC * cosA) + (cosC * sinB * sinA), (sinC * sinA) + (cosC * sinB * cosA) , 0,
//							sinC * cosB, (cosC * cosA)  + (sinC * sinB * sinA), (-cosC * sinA) + (sinC * sinB * cosA), 0,
//							-sinB	   , cosB * sinA 	  					  , cosB * cosA							 , 0,
//							0	       , 0									  , 0	 							     , 1
							cosC * cosB							 , sinC * cosB							, -sinB		 , 0,
							(-sinC * cosA) + (cosC * sinB * sinA), (cosC * cosA)  + (sinC * sinB * sinA), cosB * sinA, 0,
							(sinC * sinA) + (cosC * sinB * cosA) , (-cosC * sinA) + (sinC * sinB * cosA), cosB * cosA, 0,
							0								     , 0									, 0	 		 , 1
					};
//					return multiplyXByMatrix(x, ZYXROTATION_IDENTITY);
					return multiplyXByMatrix(ZYXROTATION_IDENTITY, x);
				}
//				temp.setVectorAt(0, cosC * cosB); temp.setVectorAt(1, (-sinC * cosA) + (cosC * sinB * sinA)); temp.setVectorAt(2, (sinC * sinA) + (cosC * sinB * cosA)) ; temp.setVectorAt(3, 0d) ;
//				temp.setVectorAt(4, sinC * cosB); temp.setVectorAt(5, (cosC * cosA)  + (sinC * sinB * sinA)); temp.setVectorAt(6, (-cosC * sinA) + (sinC * sinB * cosA)); temp.setVectorAt(7, 0d) ;
//				temp.setVectorAt(8, -sinB)		; temp.setVectorAt(9, cosB * sinA) 							; temp.setVectorAt(10, cosB * cosA)	     					; temp.setVectorAt(11, 0d);
//				temp.setVectorAt(12, 0d)		; temp.setVectorAt(13, 0d)	    							; temp.setVectorAt(14, 0d) 	      							; temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, cosC * cosB)						  ; temp.setVectorAt(1, sinC * cosB)							; temp.setVectorAt(2, -sinB)	   ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, (-sinC * cosA) + (cosC * sinB * sinA)); temp.setVectorAt(5, (cosC * cosA)  + (sinC * sinB * sinA))	; temp.setVectorAt(6, cosB * sinA) ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, (sinC * sinA) + (cosC * sinB * cosA)) ; temp.setVectorAt(9, (-cosC * sinA) + (sinC * sinB * cosA)) 	; temp.setVectorAt(10, cosB * cosA); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, 0d)								  ; temp.setVectorAt(13, 0d)	    							; temp.setVectorAt(14, 0d) 	       ; temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			public static Mat4 rotate_zyx(Mat4 x, Vec3 r, TempVec temp) {
				return rotate_zyx(x, r.x(), r.y(), r.z(), temp);
			}
			protected static Object instanceQuaternionRotate(double nx, double ny, double nz, double r, TempVec temp, int offset) {
				r /= 2; double rSin = sin(r);
				double qx = rSin * nx;
				double qy = rSin * ny;
				double qz = rSin * nz;
				double qw = cos(r);
				if(temp == null) return new Vec4(qx, qy, qz, qw);
				temp.setVectorAt(offset    	  , qx);
				temp.setVectorAt(offset + 1, qy);
				temp.setVectorAt(offset + 2, qz);
				temp.setVectorAt(offset + 3, qw);
				return temp;
			}
			public static Vec4 instanceQuaternionRotate(double nx, double ny, double nz, double r) {
				return (Vec4) instanceQuaternionRotate(nx, ny, nz, r, null, 0);
			}
			protected static Object quaternionMultiply(Object q1, Object q2, TempVec temp) {
				if(temp == null) {
					if(!(q1 instanceof Vec4) || !(q2 instanceof Vec4)) throw new IllegalStateException("State unsupported");
					Vec4 _q1 = (Vec4) q1; Vec4 _q2 = (Vec4) q2; Vec4 qr = new Vec4(0);
					qr.x((_q1.w() * _q2.x()) + (_q1.x() * _q2.w()) + (_q1.y() * _q2.z()) - (_q1.z() * _q2.y()));
					qr.y((_q1.w() * _q2.y()) - (_q1.x() * _q2.z()) + (_q1.y() * _q2.w()) + (_q1.z() * _q2.x()));
					qr.z((_q1.w() * _q2.z()) + (_q1.x() * _q2.y()) - (_q1.y() * _q2.x()) + (_q1.z() * _q2.w()));
					qr.w((_q1.w() * _q2.w()) - (_q1.x() * _q2.x()) - (_q1.y() * _q2.y()) - (_q1.z() * _q2.z()));
					return qr;
				} if(q1 instanceof Vec4 && q2 instanceof Vec4) {
					Vec4 _q1 = (Vec4) q1; Vec4 _q2 = (Vec4) q2;
					temp.setVectorAt(0, (_q1.w() * _q2.x()) + (_q1.x() * _q2.w()) + (_q1.y() * _q2.z()) - (_q1.z() * _q2.y()));
					temp.setVectorAt(1, (_q1.w() * _q2.y()) - (_q1.x() * _q2.z()) + (_q1.y() * _q2.w()) + (_q1.z() * _q2.x()));
					temp.setVectorAt(2, (_q1.w() * _q2.z()) + (_q1.x() * _q2.y()) - (_q1.y() * _q2.x()) + (_q1.z() * _q2.w()));
					temp.setVectorAt(3, (_q1.w() * _q2.w()) - (_q1.x() * _q2.x()) - (_q1.y() * _q2.y()) - (_q1.z() * _q2.z()));
					temp.cutTo(_q1, 0, 0, 4); return _q1;
				} if(q1 != temp || q2 != temp) throw new IllegalStateException("State unsupported");
				temp.setVectorAt(8 , (temp.getDoubleAt(3) * temp.getDoubleAt(4)) + (temp.getDoubleAt(0) * temp.getDoubleAt(7)) + (temp.getDoubleAt(1) * temp.getDoubleAt(6)) - (temp.getDoubleAt(2) * temp.getDoubleAt(5)));
				temp.setVectorAt(9 , (temp.getDoubleAt(3) * temp.getDoubleAt(5)) - (temp.getDoubleAt(0) * temp.getDoubleAt(6)) + (temp.getDoubleAt(1) * temp.getDoubleAt(7)) + (temp.getDoubleAt(2) * temp.getDoubleAt(4)));
				temp.setVectorAt(10, (temp.getDoubleAt(3) * temp.getDoubleAt(6)) + (temp.getDoubleAt(0) * temp.getDoubleAt(5)) - (temp.getDoubleAt(1) * temp.getDoubleAt(4)) + (temp.getDoubleAt(2) * temp.getDoubleAt(7)));
				temp.setVectorAt(11, (temp.getDoubleAt(3) * temp.getDoubleAt(7)) - (temp.getDoubleAt(0) * temp.getDoubleAt(4)) - (temp.getDoubleAt(1) * temp.getDoubleAt(5)) - (temp.getDoubleAt(2) * temp.getDoubleAt(6)));
				temp.cutTo(temp, 0, 8, 4); return temp;
			}
			public static Vec4 quaternionMultiply(Vec4 q1, Vec4 q2, TempVec temp) {
				return (Vec4) quaternionMultiply((Object) q1, (Object) q2, temp);
			}
			protected static Object instanceQuaternionRotateXYZ(double rx, double ry, double rz, TempVec temp) {
				Object qrx = instanceQuaternionRotate(1, 0, 0, rx, temp, 0);
				Object qry = instanceQuaternionRotate(0, 1, 0, ry, temp, 4);
				Object qr = quaternionMultiply(qrx, qry, temp);
				Object qrz = instanceQuaternionRotate(0, 0, 1, rz, temp, 4);
				return quaternionMultiply(qr, qrz, temp);
			}
			protected static Mat4 quaternionRotate(Mat4 x, Object quaternion) {
				TempVec temp = quaternion instanceof TempVec ? (TempVec) quaternion : null;
				double qx = temp == null ? ((Vec4) quaternion).x() : temp.getDoubleAt(0);
				double qy = temp == null ? ((Vec4) quaternion).y() : temp.getDoubleAt(1);
				double qz = temp == null ? ((Vec4) quaternion).z() : temp.getDoubleAt(2);
				double qw = temp == null ? ((Vec4) quaternion).w() : temp.getDoubleAt(3);
				double qxx = qx * qx; double qyy = qy * qy; double qzz = qz * qz;
				double qxz = qx * qz; double qyx = qy * qx; double qzy = qz * qy;
				double qxw = qx * qw; double qyw = qy * qw; double qzw = qz * qw;
				if(temp == null) {
					double[] QUATERNION_IDENTITY = new double[] {
//							1 - 2 * (qyy + qzz), 2 * (qyx - qzw)	, 2 * (qxz + qyw)    , 0,
//							2 * (qyx + qzw)	   , 1 - 2 * (qxx + qzz), 2 * (qzy - qxw)    , 0,
//							2 * (qxz - qyw)	   , 2 * (qzy + qxw)	, 1 - 2 * (qxx + qyy), 0,
//							0				   , 0					, 0				     , 1
							1 - 2 * (qyy + qzz), 2 * (qyx + qzw)	, 2 * (qxz - qyw)    , 0,
							2 * (qyx - qzw)	   , 1 - 2 * (qxx + qzz), 2 * (qzy + qxw)    , 0,
							2 * (qxz + qyw)	   , 2 * (qzy - qxw)	, 1 - 2 * (qxx + qyy), 0,
							0				   , 0					, 0				     , 1
					};
//					return multiplyXByMatrix(x, QUATERNION_IDENTITY);
					return multiplyXByMatrix(QUATERNION_IDENTITY, x);
				}
//				temp.setVectorAt(0, 1 - 2 * (qyy + qzz)); temp.setVectorAt(1, 2 * (qyx - qzw))    ; temp.setVectorAt(2, 2 * (qxz + qyw))	   ; temp.setVectorAt(3, 0d) ;
//				temp.setVectorAt(4, 2 * (qyx + qzw))    ; temp.setVectorAt(5, 1 - 2 * (qxx + qzz)); temp.setVectorAt(6, 2 * (qzy - qxw))	   ; temp.setVectorAt(7, 0d) ;
//				temp.setVectorAt(8, 2 * (qxz - qyw))    ; temp.setVectorAt(9, 2 * (qzy + qxw))    ; temp.setVectorAt(10, 1 - 2 * (qxx + qyy)); temp.setVectorAt(11, 0d);
//				temp.setVectorAt(12, 0d)		 	     ; temp.setVectorAt(13, 0d)	    	    ; temp.setVectorAt(14, 0d) 	    	   ; temp.setVectorAt(15, 1d);
				temp.setVectorAt(0, 1 - 2 * (qyy + qzz)); temp.setVectorAt(1, 2 * (qyx + qzw))    ; temp.setVectorAt(2, 2 * (qxz - qyw))	   ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, 2 * (qyx - qzw))    ; temp.setVectorAt(5, 1 - 2 * (qxx + qzz)); temp.setVectorAt(6, 2 * (qzy + qxw))	   ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, 2 * (qxz + qyw))    ; temp.setVectorAt(9, 2 * (qzy - qxw))    ; temp.setVectorAt(10, 1 - 2 * (qxx + qyy)); temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, 0d)		 	     ; temp.setVectorAt(13, 0d)	    	    ; temp.setVectorAt(14, 0d) 	    	   ; temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			@Deprecated public static Mat4 quaternionRotate(Mat4 x, double rx, double ry, double rz, TempVec temp) { // Quaternion acts like euler angle
				return quaternionRotate(x, instanceQuaternionRotateXYZ(rx, ry, rz, temp));
			}
			public static Mat4 quaternionRotate(Mat4 x, double drx, double dry, double drz, Vec4 oldQuaternion, TempVec temp) { // Real quaternion
				Object quaternion = instanceQuaternionRotateXYZ(drx, dry, drz, temp);
				if(temp != null) temp.copyFrom(oldQuaternion, 0, 4, 4);
				quaternion = quaternionMultiply(temp != null ? temp : oldQuaternion, quaternion, temp);
				if(temp != null) temp.copyTo(oldQuaternion, 0, 0, 4);
				else { Vec4 quat = (Vec4) quaternion; oldQuaternion.set(quat.x(), quat.y(), quat.z(), quat.w()); }
				return quaternionRotate(x, quaternion);
			}
		}
		public static class Projection {
			public static Mat4 projection(Mat4 x, Size windowSize, double fov, double distanceNear, double distanceFar, TempVec temp) {
				double aspectRatio = (double) windowSize.getWidth() / (double) windowSize.getHeight();
				double yScale = (1.0d / tan(radians(fov / 2.0d))) * aspectRatio;
				double xScale = yScale / aspectRatio;
				double frustumLength = distanceFar - distanceNear;
				double planeSum = distanceFar + distanceNear;
				double planeMul = 2 * distanceFar * distanceNear;

				if(temp == null) {
					double[] PROJECTION_IDENTITY = new double[] {
//							xScale, 0	  , 0						   , 0 						    ,
//							0 	  , yScale, 0						   , 0						    ,
//							0	  , 0 	  , -(planeSum / frustumLength), -(planeMul / frustumLength),
//							0	  , 0	  , -1	    				   , 0
							xScale, 0	  , 0						   , 0 						    ,
							0 	  , yScale, 0						   , 0						    ,
							0	  , 0 	  , -(planeSum / frustumLength), -1							,
							0	  , 0	  , -(planeMul / frustumLength), 0
					};
//					return multiplyXByMatrix(x, PROJECTION_IDENTITY);
					return multiplyXByMatrix(PROJECTION_IDENTITY, x);
				}
//				temp.setVectorAt(0, xScale) 	  ; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) 					; temp.setVectorAt(3, 0d) 				  ;
//				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, yScale) 	  ; temp.setVectorAt(6, 0d) 					; temp.setVectorAt(7, 0d) 				  ;
//				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, -(planeSum / frustumLength)); temp.setVectorAt(11, -(planeMul / frustumLength));
//				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d); temp.setVectorAt(14, -1d)			 	    ; temp.setVectorAt(15, 0d) 				  ;
				temp.setVectorAt(0, xScale) 	  ; temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) 					; temp.setVectorAt(3, 0d) 				  ;
				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, yScale) 	  ; temp.setVectorAt(6, 0d) 					; temp.setVectorAt(7, 0d) 				  ;
				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, -(planeSum / frustumLength)); temp.setVectorAt(11, -1d)				  ;
				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d); temp.setVectorAt(14, -(planeMul / frustumLength)); temp.setVectorAt(15, 0d) 				  ;
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
			public static Mat4 ortho(Mat4 x, double left, double top, double right, double bottom, double zNear, double zFar, TempVec temp) {
				double rightLeftSub = right - left;
				double topBottomSub = top - bottom;
				double farNearSub = zFar - zNear;
				double rightLeft2 = 2 / rightLeftSub;
				double topBottom2 = 2 / topBottomSub;
				double farNear2 = -2 / farNearSub;

				if(temp == null) {
					double[] ORTHO_IDENTITY = new double[] {
//							rightLeft2, 0	  	  , 0		, -((right + left) / rightLeftSub),
//							0 	  	  , topBottom2, 0		, -((top + bottom) / topBottomSub),
//							0	  	  , 0 	  	  , farNear2, -((zNear + zFar) / farNearSub)  ,
//							0	  	  , 0	  	  , 0	    , 1
							rightLeft2						, 0	  	  						  , 0							  , 0,
							0 	  	  						, topBottom2					  , 0							  , 0,
							0	  	  						, 0 	  	  					  , farNear2					  , 0,
							-((right + left) / rightLeftSub), -((top + bottom) / topBottomSub), -((zNear + zFar) / farNearSub), 1
					};
//					return multiplyXByMatrix(x, ORTHO_IDENTITY);
					return multiplyXByMatrix(ORTHO_IDENTITY, x);
				}
//				temp.setVectorAt(0, rightLeft2); temp.setVectorAt(1, 0d) ; temp.setVectorAt(2, 0d) ; temp.setVectorAt(3, -((right + left) / rightLeftSub));
//				temp.setVectorAt(4, 0d) ; temp.setVectorAt(5, topBottom2); temp.setVectorAt(6, 0d) ; temp.setVectorAt(7, -((top + bottom) / topBottomSub));
//				temp.setVectorAt(8, 0d) ; temp.setVectorAt(9, 0d) ; temp.setVectorAt(10, farNear2) ; temp.setVectorAt(11, -((zNear + zFar) / farNearSub)) ;
//				temp.setVectorAt(12, 0d); temp.setVectorAt(13, 0d); temp.setVectorAt(14, 0d); temp.setVectorAt(15, 1d) 				  	    ;
				temp.setVectorAt(0, rightLeft2)						 ; temp.setVectorAt(1, 0d) 					   ; temp.setVectorAt(2, 0d)					   ; temp.setVectorAt(3, 0d) ;
				temp.setVectorAt(4, 0d) 						 ; temp.setVectorAt(5, topBottom2)					   ; temp.setVectorAt(6, 0d)					   ; temp.setVectorAt(7, 0d) ;
				temp.setVectorAt(8, 0d) 						 ; temp.setVectorAt(9, 0d) 					   ; temp.setVectorAt(10, farNear2)					   ; temp.setVectorAt(11, 0d);
				temp.setVectorAt(12, -((right + left) / rightLeftSub)); temp.setVectorAt(13, -((top + bottom) / topBottomSub)); temp.setVectorAt(14, -((zNear + zFar) / farNearSub)); temp.setVectorAt(15, 1d);
//				return multiplyXByMatrix(x, temp);
				return multiplyXByMatrix(temp, x);
			}
		}
		public static class View {
			// http://planning.cs.uiuc.edu/node102.html
			// It is important to note that performs the roll first, then the pitch, and finally the yaw.
			public static Mat4 view(Mat4 x, double yaw, double pitch, double roll, Vec3 location, TempVec temp) {
//				Transformation.rotate_x(x, roll, temp);
//				Transformation.rotate_y(x, pitch, temp);
//				Transformation.rotate_z(x, yaw, temp);
//				Transformation.rotate_z(x, roll, temp);
//				Transformation.rotate_x(x, pitch, temp);
//				Transformation.rotate_y(x, yaw, temp);
//				Transformation.rotate_xyz(x, yaw, pitch, roll, temp);
				Transformation.rotate_xyz(x, pitch, yaw, roll, temp);
				Transformation.translate(x, -location.x(), -location.y(), -location.z(), temp);
				return x;
			}
			@Deprecated public static Mat4 viewQuaternion(Mat4 x, double yaw, double pitch, double roll, Vec3 location, TempVec temp) { // Quaternion acts like euler angle
				Transformation.quaternionRotate(x, pitch, yaw, roll, temp);
				Transformation.translate(x, -location.x(), -location.y(), -location.z(), temp);
				return x;
			}
			public static Mat4 viewQuaternion(Mat4 x, double dyaw, double dpitch, double droll, Vec4 oldQuaternion, Vec3 location, TempVec temp) { // Real quaternion
				Transformation.quaternionRotate(x, dpitch, dyaw, droll, oldQuaternion, temp);
				Transformation.translate(x, -location.x(), -location.y(), -location.z(), temp);
				return x;
			}
		}
	}

	public static void toBuffer(int[] vec, int vecOff, Buffer buffer, int bufferOff, int length) { UnsafeUtils.__copyMemory(vec, Unsafe.ARRAY_INT_BASE_OFFSET + vecOff * Integer.BYTES, null, BufferUtils.__getAddress(buffer) + bufferOff, length * Integer.BYTES); }
	public static void toBuffer(long[] vec, int vecOff, Buffer buffer, int bufferOff, int length) { UnsafeUtils.__copyMemory(vec, Unsafe.ARRAY_LONG_BASE_OFFSET + vecOff * Long.BYTES, null, BufferUtils.__getAddress(buffer) + bufferOff, length * Long.BYTES); }
	public static void toBuffer(short[] vec, int vecOff, Buffer buffer, int bufferOff, int length) { UnsafeUtils.__copyMemory(vec, Unsafe.ARRAY_SHORT_BASE_OFFSET + vecOff * Short.BYTES, null, BufferUtils.__getAddress(buffer) + bufferOff, length * Short.BYTES); }
	public static void toBuffer(float[] vec, int vecOff, Buffer buffer, int bufferOff, int length) { UnsafeUtils.__copyMemory(vec, Unsafe.ARRAY_FLOAT_BASE_OFFSET + vecOff * Float.BYTES, null, BufferUtils.__getAddress(buffer) + bufferOff, length * Float.BYTES); }
	public static void toBuffer(double[] vec, int vecOff, Buffer buffer, int bufferOff, int length) { UnsafeUtils.__copyMemory(vec, Unsafe.ARRAY_DOUBLE_BASE_OFFSET + vecOff * Double.BYTES, null, BufferUtils.__getAddress(buffer) + bufferOff, length * Double.BYTES); }
	public static void toBuffer(IVecN vec, int vecOff, Buffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff, length); }
	public static void toBuffer(LVecN vec, int vecOff, Buffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff, length); }
	public static void toBuffer(SVecN vec, int vecOff, Buffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff, length); }
	public static void toBuffer(FVecN vec, int vecOff, Buffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff, length); }
	public static void toBuffer(VecN vec, int vecOff, Buffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff, length); }
	public static void toBuffer(IMatMxN mat, int matOff, Buffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff, length); }
	public static void toBuffer(LMatMxN mat, int matOff, Buffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff, length); }
	public static void toBuffer(SMatMxN mat, int matOff, Buffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff, length); }
	public static void toBuffer(FMatMxN mat, int matOff, Buffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff, length); }
	public static void toBuffer(MatMxN mat, int matOff, Buffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff, length); }
	public static void toBuffer(IVecN vec, int vecOff, Buffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(LVecN vec, int vecOff, Buffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(SVecN vec, int vecOff, Buffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(FVecN vec, int vecOff, Buffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(VecN vec, int vecOff, Buffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(IMatMxN mat, int matOff, Buffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(LMatMxN mat, int matOff, Buffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(SMatMxN mat, int matOff, Buffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(FMatMxN mat, int matOff, Buffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(MatMxN mat, int matOff, Buffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(IVecN vec, int vecOff, Buffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(LVecN vec, int vecOff, Buffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(SVecN vec, int vecOff, Buffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(FVecN vec, int vecOff, Buffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(VecN vec, int vecOff, Buffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(IMatMxN mat, int matOff, Buffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(LMatMxN mat, int matOff, Buffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(SMatMxN mat, int matOff, Buffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(FMatMxN mat, int matOff, Buffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(MatMxN mat, int matOff, Buffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(IVecN vec, Buffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(LVecN vec, Buffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(SVecN vec, Buffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(FVecN vec, Buffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(VecN vec, Buffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(IMatMxN mat, Buffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(LMatMxN mat, Buffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(SMatMxN mat, Buffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(FMatMxN mat, Buffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(MatMxN mat, Buffer buffer) { toBuffer(mat, 0, buffer); }

	public static void toBuffer(IVecN vec, int vecOff, IntBuffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff * Integer.BYTES, length); }
	public static void toBuffer(LVecN vec, int vecOff, LongBuffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff * Long.BYTES, length); }
	public static void toBuffer(SVecN vec, int vecOff, ShortBuffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff * Short.BYTES, length); }
	public static void toBuffer(FVecN vec, int vecOff, FloatBuffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff * Float.BYTES, length); }
	public static void toBuffer(VecN vec, int vecOff, DoubleBuffer buffer, int bufferOff, int length) { toBuffer(vec.d, vecOff, buffer, bufferOff * Double.BYTES, length); }
	public static void toBuffer(IMatMxN mat, int matOff, IntBuffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff * Integer.BYTES, length); }
	public static void toBuffer(LMatMxN mat, int matOff, LongBuffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff * Long.BYTES, length); }
	public static void toBuffer(SMatMxN mat, int matOff, ShortBuffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff * Short.BYTES, length); }
	public static void toBuffer(FMatMxN mat, int matOff, FloatBuffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff * Float.BYTES, length); }
	public static void toBuffer(MatMxN mat, int matOff, DoubleBuffer buffer, int bufferOff, int length) { toBuffer(mat.d, matOff, buffer, bufferOff * Double.BYTES, length); }
	public static void toBuffer(IVecN vec, int vecOff, IntBuffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(LVecN vec, int vecOff, LongBuffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(SVecN vec, int vecOff, ShortBuffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(FVecN vec, int vecOff, FloatBuffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(VecN vec, int vecOff, DoubleBuffer buffer, int bufferOff) { toBuffer(vec, vecOff, buffer, bufferOff, vec.d.length - vecOff); }
	public static void toBuffer(IMatMxN mat, int matOff, IntBuffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(LMatMxN mat, int matOff, LongBuffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(SMatMxN mat, int matOff, ShortBuffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(FMatMxN mat, int matOff, FloatBuffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(MatMxN mat, int matOff, DoubleBuffer buffer, int bufferOff) { toBuffer(mat, matOff, buffer, bufferOff, mat.d.length - matOff); }
	public static void toBuffer(IVecN vec, int vecOff, IntBuffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(LVecN vec, int vecOff, LongBuffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(SVecN vec, int vecOff, ShortBuffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(FVecN vec, int vecOff, FloatBuffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(VecN vec, int vecOff, DoubleBuffer buffer) { toBuffer(vec, vecOff, buffer, 0); }
	public static void toBuffer(IMatMxN mat, int matOff, IntBuffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(LMatMxN mat, int matOff, LongBuffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(SMatMxN mat, int matOff, ShortBuffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(FMatMxN mat, int matOff, FloatBuffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(MatMxN mat, int matOff, DoubleBuffer buffer) { toBuffer(mat, matOff, buffer, 0); }
	public static void toBuffer(IVecN vec, IntBuffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(LVecN vec, LongBuffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(SVecN vec, ShortBuffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(FVecN vec, FloatBuffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(VecN vec, DoubleBuffer buffer) { toBuffer(vec, 0, buffer); }
	public static void toBuffer(IMatMxN mat, IntBuffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(LMatMxN mat, LongBuffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(SMatMxN mat, ShortBuffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(FMatMxN mat, FloatBuffer buffer) { toBuffer(mat, 0, buffer); }
	public static void toBuffer(MatMxN mat, DoubleBuffer buffer) { toBuffer(mat, 0, buffer); }

	public static void fromBuffer(Buffer buffer, int bufferOff, int[] vec, int vecOff, int length) { UnsafeUtils.__copyMemory(null, BufferUtils.__getAddress(buffer) + bufferOff, vec, Unsafe.ARRAY_INT_BASE_OFFSET + vecOff * Integer.BYTES, length * Integer.BYTES); }
	public static void fromBuffer(Buffer buffer, int bufferOff, long[] vec, int vecOff, int length) { UnsafeUtils.__copyMemory(null, BufferUtils.__getAddress(buffer) + bufferOff, vec, Unsafe.ARRAY_LONG_BASE_OFFSET + vecOff * Long.BYTES, length * Long.BYTES); }
	public static void fromBuffer(Buffer buffer, int bufferOff, short[] vec, int vecOff, int length) { UnsafeUtils.__copyMemory(null, BufferUtils.__getAddress(buffer) + bufferOff, vec, Unsafe.ARRAY_SHORT_BASE_OFFSET + vecOff * Short.BYTES, length * Short.BYTES); }
	public static void fromBuffer(Buffer buffer, int bufferOff, float[] vec, int vecOff, int length) { UnsafeUtils.__copyMemory(null, BufferUtils.__getAddress(buffer) + bufferOff, vec, Unsafe.ARRAY_FLOAT_BASE_OFFSET + vecOff * Float.BYTES, length * Float.BYTES); }
	public static void fromBuffer(Buffer buffer, int bufferOff, double[] vec, int vecOff, int length) { UnsafeUtils.__copyMemory(null, BufferUtils.__getAddress(buffer) + bufferOff, vec, Unsafe.ARRAY_DOUBLE_BASE_OFFSET + vecOff * Double.BYTES, length * Double.BYTES); }
	public static void fromBuffer(Buffer buffer, int bufferOff, IVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff, vec.d, vecOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, LVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff, vec.d, vecOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, SVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff, vec.d, vecOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, FVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff, vec.d, vecOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, VecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff, vec.d, vecOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, IMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff, mat.d, matOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, LMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff, mat.d, matOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, SMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff, mat.d, matOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, FMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff, mat.d, matOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, MatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff, mat.d, matOff, length); }
	public static void fromBuffer(Buffer buffer, int bufferOff, IVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, LVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, SVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, FVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, VecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, IMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, LMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, SMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, FMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(Buffer buffer, int bufferOff, MatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(Buffer buffer, IVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(Buffer buffer, LVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(Buffer buffer, SVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(Buffer buffer, FVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(Buffer buffer, VecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(Buffer buffer, IMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(Buffer buffer, LMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(Buffer buffer, SMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(Buffer buffer, FMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(Buffer buffer, MatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(Buffer buffer, IVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(Buffer buffer, LVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(Buffer buffer, SVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(Buffer buffer, FVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(Buffer buffer, VecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(Buffer buffer, IMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(Buffer buffer, LMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(Buffer buffer, SMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(Buffer buffer, FMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(Buffer buffer, MatMxN mat) { fromBuffer(buffer, mat, 0); }

	public static void fromBuffer(IntBuffer buffer, int bufferOff, IVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff * Integer.BYTES, vec.d, vecOff, length); }
	public static void fromBuffer(LongBuffer buffer, int bufferOff, LVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff * Long.BYTES, vec.d, vecOff, length); }
	public static void fromBuffer(ShortBuffer buffer, int bufferOff, SVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff * Short.BYTES, vec.d, vecOff, length); }
	public static void fromBuffer(FloatBuffer buffer, int bufferOff, FVecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff * Float.BYTES, vec.d, vecOff, length); }
	public static void fromBuffer(DoubleBuffer buffer, int bufferOff, VecN vec, int vecOff, int length) { fromBuffer(buffer, bufferOff * Double.BYTES, vec.d, vecOff, length); }
	public static void fromBuffer(IntBuffer buffer, int bufferOff, IMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff * Integer.BYTES, mat.d, matOff, length); }
	public static void fromBuffer(LongBuffer buffer, int bufferOff, LMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff * Long.BYTES, mat.d, matOff, length); }
	public static void fromBuffer(ShortBuffer buffer, int bufferOff, SMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff * Short.BYTES, mat.d, matOff, length); }
	public static void fromBuffer(FloatBuffer buffer, int bufferOff, FMatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff * Float.BYTES, mat.d, matOff, length); }
	public static void fromBuffer(DoubleBuffer buffer, int bufferOff, MatMxN mat, int matOff, int length) { fromBuffer(buffer, bufferOff * Double.BYTES, mat.d, matOff, length); }
	public static void fromBuffer(IntBuffer buffer, int bufferOff, IVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(LongBuffer buffer, int bufferOff, LVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(ShortBuffer buffer, int bufferOff, SVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(FloatBuffer buffer, int bufferOff, FVecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(DoubleBuffer buffer, int bufferOff, VecN vec, int vecOff) { fromBuffer(buffer, bufferOff, vec, vecOff, vec.d.length - vecOff); }
	public static void fromBuffer(IntBuffer buffer, int bufferOff, IMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(LongBuffer buffer, int bufferOff, LMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(ShortBuffer buffer, int bufferOff, SMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(FloatBuffer buffer, int bufferOff, FMatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(DoubleBuffer buffer, int bufferOff, MatMxN mat, int matOff) { fromBuffer(buffer, bufferOff, mat, matOff, mat.d.length - matOff); }
	public static void fromBuffer(IntBuffer buffer, IVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(LongBuffer buffer, LVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(ShortBuffer buffer, SVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(FloatBuffer buffer, FVecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(DoubleBuffer buffer, VecN vec, int vecOff) { fromBuffer(buffer, 0, vec, vecOff); }
	public static void fromBuffer(IntBuffer buffer, IMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(LongBuffer buffer, LMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(ShortBuffer buffer, SMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(FloatBuffer buffer, FMatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(DoubleBuffer buffer, MatMxN mat, int matOff) { fromBuffer(buffer, 0, mat, matOff); }
	public static void fromBuffer(IntBuffer buffer, IVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(LongBuffer buffer, LVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(ShortBuffer buffer, SVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(FloatBuffer buffer, FVecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(DoubleBuffer buffer, VecN vec) { fromBuffer(buffer, vec, 0); }
	public static void fromBuffer(IntBuffer buffer, IMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(LongBuffer buffer, LMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(ShortBuffer buffer, SMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(FloatBuffer buffer, FMatMxN mat) { fromBuffer(buffer, mat, 0); }
	public static void fromBuffer(DoubleBuffer buffer, MatMxN mat) { fromBuffer(buffer, mat, 0); }
}
